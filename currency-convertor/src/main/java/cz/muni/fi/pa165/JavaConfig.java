package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;
import cz.muni.fi.pa165.currency.ExchangeRateTable;
import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
@ComponentScan(basePackageClasses = cz.muni.fi.pa165.AspectForDuration.class)
public class JavaConfig {
    @Bean(name = "exchangeRateTable")
    public ExchangeRateTable getExchangeRateTable(){
        ExchangeRateTable exchangeRateTable = new ExchangeRateTableImpl();
        return exchangeRateTable;
    }


    @Bean(name = "currencyConvertor")
    public CurrencyConvertor getCurrencyConvertor(){
        CurrencyConvertor currencyConvertor = new CurrencyConvertorImpl(getExchangeRateTable());
        return currencyConvertor;
    }
}
