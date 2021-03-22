package cz.muni.fi.pa165;

import cz.muni.fi.pa165.currency.CurrencyConvertor;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.Currency;

public class MainXml {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");


        CurrencyConvertor currencyConvertor = applicationContext.getBean(CurrencyConvertor.class);

        BigDecimal test = currencyConvertor.convert(Currency.getInstance("EUR"), Currency.getInstance("CZK"), BigDecimal.valueOf(10));
        System.out.println("test value = "+ test);
    }
}
