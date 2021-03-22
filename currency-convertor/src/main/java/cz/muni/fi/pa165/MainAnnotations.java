package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;
import cz.muni.fi.pa165.currency.CurrencyConvertorImpl;

import cz.muni.fi.pa165.currency.ExchangeRateTableImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainAnnotations {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("cz/muni/fi/pa165/currency");

        CurrencyConvertor currencyConvertor = context.getBean(CurrencyConvertorImpl.class);

        BigDecimal test = currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.valueOf(1));
        System.out.println("test value2 = "+ test);
        }
    }

