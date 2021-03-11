package cz.muni.fi.pa165.currency;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CurrencyConvertorImplTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Mock
    ExchangeRateTable exchangeRateTable;
    CurrencyConvertor currencyConvertor;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        currencyConvertor = new CurrencyConvertorImpl(exchangeRateTable);
    }

    @Test
    public void testConvert() throws ExternalServiceFailureException {
        // Don't forget to test border values and proper rounding.

        Currency c1 = Currency.getInstance("EUR");
        Currency c2 = Currency.getInstance("CZK");

        when(exchangeRateTable.getExchangeRate(c1,c2)).thenReturn(BigDecimal.valueOf(25));

        BigDecimal value = new BigDecimal("15.29");
        assertEquals(currencyConvertor.convert(c1,c2,value),BigDecimal.valueOf(382.25));


    }

    @Test
    public void testConvertWithNullSourceCurrency() throws ExternalServiceFailureException {
        Currency c1 = null;
        Currency c2 = Currency.getInstance("CZK");
        BigDecimal value = new BigDecimal("15.29");

        when(exchangeRateTable.getExchangeRate(c1,c2)).thenReturn(BigDecimal.valueOf(25));

        exceptionRule.expect(NullPointerException.class);

        currencyConvertor.convert(c1,c2,value);

    }

    @Test
    public void testConvertWithNullTargetCurrency() throws ExternalServiceFailureException {
        Currency c1 = Currency.getInstance("EUR");
        Currency c2 = null;
        BigDecimal value = new BigDecimal("15.29");

        when(exchangeRateTable.getExchangeRate(c1,c2)).thenReturn(BigDecimal.valueOf(25));

        exceptionRule.expect(NullPointerException.class);

        currencyConvertor.convert(c1,c2,value);
    }

    @Test
    public void testConvertWithNullSourceAmount() throws ExternalServiceFailureException {
        Currency c1 = Currency.getInstance("EUR");
        Currency c2 = Currency.getInstance("CZK");
        BigDecimal value = null;

        when(exchangeRateTable.getExchangeRate(c1,c2)).thenReturn(BigDecimal.valueOf(25));

        exceptionRule.expect(NullPointerException.class);

        currencyConvertor.convert(c1,c2,value);
    }

    @Test
    public void testConvertWithUnknownCurrency() throws ExternalServiceFailureException {


        Currency c1 = Currency.getInstance("EUR");
        Currency c2 = Currency.getInstance("CZK");
        BigDecimal value = new BigDecimal("15.29");

        when(exchangeRateTable.getExchangeRate(c1,c2)).thenReturn(BigDecimal.ZERO);

        exceptionRule.expect(UnknownExchangeRateException.class);

        currencyConvertor.convert(c1,c2,value);


    }

    @Test
    public void testConvertWithExternalServiceFailure() throws ExternalServiceFailureException {
        Currency c1 = Currency.getInstance("EUR");
        Currency c2 = Currency.getInstance("CZK");
        BigDecimal value = new BigDecimal("15.29");
        when(exchangeRateTable.getExchangeRate(c1,c2)).thenThrow(ExternalServiceFailureException.class);
        exceptionRule.expect(ExternalServiceFailureException.class);
        currencyConvertor.convert(c1,c2,value);
    }

}
