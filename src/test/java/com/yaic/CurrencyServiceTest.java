package com.yaic;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yaic.config.PlatformConfig;
import com.yaic.servicelayer.currency.CurrencyService;
import com.yaic.servicelayer.util.DecimalUtil;
import com.yaic.util.EpolicyUtils;
import com.yaic.util.MoneyStrUtil;

/**
 * test CurrencyService
 * @author wangwf
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ PlatformConfig.class })
public class CurrencyServiceTest{
	
	@Resource
	private CurrencyService currencyService;
	
	@Ignore
	@Test
	public void testConvertToRMBCapital()
	{
		final String string1 = "10.38123456";
		final String string2 = "120.3";
		final String string3 = "10200.35";
		final String string4 = "0.00";
		final String string5 = "-0.04";
		
		final BigDecimal string11 = DecimalUtil.toBigDecimal(string1);
		final BigDecimal string12 = DecimalUtil.toBigDecimal(string2);
		final BigDecimal string13 = DecimalUtil.toBigDecimal(string3);
		final BigDecimal string14 = DecimalUtil.toBigDecimal(string4);
		final BigDecimal string15 = DecimalUtil.toBigDecimal(string5);

		/*System.out.println("1:" + string11.toString());
		System.out.println("2:" + string12.toString());
		System.out.println("3:" + string13.toString());
		System.out.println("4:" + string14.toString());
		System.out.println("5:" + string15.toString());*/
		
		System.out.println(" ----------------- currencyService result -----------------------");
		
		/*System.out.println("1:" + currencyService.convertToRMBCapital(string1));
		System.out.println("2:" + currencyService.convertToRMBCapital(string2));
		System.out.println("3:" + currencyService.convertToRMBCapital(string3));
		System.out.println("4:" + currencyService.convertToRMBCapital(string4));
		System.out.println("5:" + currencyService.convertToRMBCapital(string5));*/
		
		/*System.out.println("1:" + currencyService.convertToRMBCapital(string11.toString()));
		System.out.println("2:" + currencyService.convertToRMBCapital(string12.toString()));
		System.out.println("3:" + currencyService.convertToRMBCapital(string13.toString()));
		System.out.println("4:" + currencyService.convertToRMBCapital(string14.toString()));
		System.out.println("5:" + currencyService.convertToRMBCapital(string15.toString()));*/
		
		System.out.println(" ----------------- EpolicyUtils result -----------------------");

		/*System.out.println("1:" + EpolicyUtils.numberCNMontrayUnit(DecimalUtil.toBigDecimal(string1)));
		System.out.println("2:" + EpolicyUtils.numberCNMontrayUnit(DecimalUtil.toBigDecimal(string2)));
		System.out.println("3:" + EpolicyUtils.numberCNMontrayUnit(DecimalUtil.toBigDecimal(string3)));
		System.out.println("4:" + EpolicyUtils.numberCNMontrayUnit(DecimalUtil.toBigDecimal(string4)));
		System.out.println("5:" + EpolicyUtils.numberCNMontrayUnit(DecimalUtil.toBigDecimal(string5)));*/
		
		System.out.println("1:" + EpolicyUtils.numberCNMontrayUnit(string11));
		System.out.println("2:" + EpolicyUtils.numberCNMontrayUnit(string12));
		System.out.println("3:" + EpolicyUtils.numberCNMontrayUnit(string13));
		System.out.println("4:" + EpolicyUtils.numberCNMontrayUnit(string14));
		System.out.println("5:" + EpolicyUtils.numberCNMontrayUnit(string15));
		
	}
	
	@Test
	public void testFormatCurrency()
	{
		/*final String string1 = "-1020012.3";
		final Double string2 = Double.valueOf(string1);
		final Long string3 = Long.valueOf("1020012");
		Boolean symbol = null;*/
		String string1 = ".123";
		String string2 = "0.01";
		String string3 = "-12";
		String string4 = "-0.123";
		String string5 = ". 1";
		String string6 = "10.";
		String string7 = "123123";
		String string8 = "1231.23";


		
		System.out.println(" ----------------- currencyService result -----------------------");
		/*System.out.println(currencyService.formatMoney(string1, symbol));
		System.out.println(currencyService.formatMoney(string2, symbol));
		System.out.println(currencyService.formatMoney(string3, symbol));*/
		System.out.println(currencyService.isValid(string1));
		System.out.println(currencyService.isValid(string2));
		System.out.println(currencyService.isValid(string3));
		System.out.println(currencyService.isValid(string4));
		System.out.println(currencyService.isValid(string5));
		System.out.println(currencyService.isValid(string6));
		System.out.println(currencyService.isValid(string7));
		long l1 = System.currentTimeMillis();
		System.out.println(currencyService.isValid(string8));
		long l2 = System.currentTimeMillis();
		System.out.println(l2-l1);


		
		System.out.println(" ----------------- EpolicyUtils result -----------------------");

		//String symbol = Currency.getInstance(Locale.getDefault()).getSymbol();
		/*System.out.println(MoneyStrUtil.formatMoney(string1,symbol));
		System.out.println(MoneyStrUtil.formatMoney(string2,symbol));
		System.out.println(MoneyStrUtil.formatMoney(string3,symbol));*/
        System.out.println(MoneyStrUtil.isNumber(string1));
        System.out.println(MoneyStrUtil.isNumber(string2));
        System.out.println(MoneyStrUtil.isNumber(string3));
        System.out.println(MoneyStrUtil.isNumber(string4));
        System.out.println(MoneyStrUtil.isNumber(string5));
        System.out.println(MoneyStrUtil.isNumber(string6));
        System.out.println(MoneyStrUtil.isNumber(string7));
		long l3 = System.currentTimeMillis();
        System.out.println(MoneyStrUtil.isNumber(string8));
		long l4 = System.currentTimeMillis();
        System.out.println(l4 - l3);
	}

}
