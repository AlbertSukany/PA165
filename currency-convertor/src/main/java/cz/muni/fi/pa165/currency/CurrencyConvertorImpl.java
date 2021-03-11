package cz.muni.fi.pa165.currency;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;


/**
 * This is base implementation of {@link CurrencyConvertor}.
 *
 * @author petr.adamek@embedit.cz
 */
public class CurrencyConvertorImpl implements CurrencyConvertor {


    Logger log = LoggerFactory.getLogger(CurrencyConvertorImpl.class);

    private final ExchangeRateTable exchangeRateTable;


    public CurrencyConvertorImpl(ExchangeRateTable exchangeRateTable) {
        this.exchangeRateTable = exchangeRateTable;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal sourceAmount) throws ExternalServiceFailureException, NullPointerException {

        log.trace("convert function called");

        if (sourceCurrency == null){

            throw new NullPointerException("source is null");
        }

        if (targetCurrency == null){

            throw new NullPointerException("target is null");
        }

        if (sourceAmount == null){

            throw new NullPointerException("value is null");
        }

        Set<Currency> available = Currency.getAvailableCurrencies();



        BigDecimal rate;


        try{
            rate = exchangeRateTable.getExchangeRate(sourceCurrency,targetCurrency);
            if (rate.equals(BigDecimal.ZERO)){
                log.warn("UnknownExchangeRateException");
                throw new UnknownExchangeRateException("currency not found");
            }
        } catch (ExternalServiceFailureException e){
            log.error("external service failure");
            throw new ExternalServiceFailureException("external service failure");
        }

        log.info("value returned");
        return rate.multiply(sourceAmount);


    }

}
