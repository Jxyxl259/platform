package com.yaic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.yaic.config.PlatformConfig;
import com.yaic.servicelayer.app.provider.AppNoProvider;
import com.yaic.servicelayer.constant.AppSystem;
import com.yaic.servicelayer.currency.CurrencyService;
import com.yaic.servicelayer.datetime.TimeService;
import com.yaic.servicelayer.email.EmailCheckService;
import com.yaic.servicelayer.http.HttpTransceiver;
import com.yaic.servicelayer.http.wrapper.HttpResponseWrapper;
import com.yaic.servicelayer.idcard.IdcardNoService;
import com.yaic.servicelayer.idcard.impl.IdcardNoServiceImpl;
import com.yaic.servicelayer.random.RandomGenerator;
import com.yaic.servicelayer.util.StringUtil;
import com.yaic.servicelayer.util.TimeUtil;


/**
 * @author Qu Dihuai
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ PlatformConfig.class })
public class SpringJUnitTest
{
	@Resource
	private TimeService timeService;

	@Resource
	private IdcardNoService idcardNoService;

	@Resource
	private EmailCheckService emailCheckService;

	@Resource
	private CurrencyService currencyService;

	/**
	 * 测试Spring的配置
	 */
	@Test
	public void testContext()
	{
		final Date currentTime = timeService.getCurrentTime();
		System.out.println("获取当前的系统时间:" + currentTime);
		System.out.println("调用Date的toString方法的结果为:" + currentTime.toString());
		System.out.println("按照格式'yyyy-MM-dd'格式化的结果为: " + TimeUtil.format(currentTime, TimeUtil.ISO_DATE_FORMAT));
		System.out.println("按照格式'yyyy-MM-dd'转化后的结果为: "
				+ TimeUtil.parseDate(TimeUtil.format(currentTime, TimeUtil.ISO_DATE_FORMAT), TimeUtil.ISO_DATE_FORMAT));
		//		try
		//		{
		//			System.out.println("按照格式'yyyy-MM-dd'转化后的结果为: " + TimeUtil.parseDate(currentTime.toString(), TimeUtil.ISO_DATE_FORMAT));
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
	}

	/**
	 * 测试TimeUtil
	 */
	@Test
	public void testDate()
	{
		final Date time = TimeUtil.beginOfDay(new Date());
		System.out.println("获取今天的开始时间:" + time);
		System.out.println("调用Date的toString方法的结果为:" + time.toString());
		System.out.println("按照格式'yyyy-MM-dd'格式化的结果为: " + TimeUtil.format(time, TimeUtil.ISO_DATE_FORMAT));
		System.out.println("按照格式'yyyy-MM-dd'转化后的结果为: "
				+ TimeUtil.parseDate(TimeUtil.format(time, TimeUtil.ISO_DATE_FORMAT), TimeUtil.ISO_DATE_FORMAT));
	}

	/**
	 * 测试身份证号码
	 */
	@Test
	public void testIdcardNo()
	{
		final String message = "身份证号[%s]是否合法:%s";
//		final String idcardNo = "621122900421063";
//		final String idcardNo1 = "61012619961113562X";
//		final String idcardNo2 = "61012619961113562X";
		final String idcardNo3 = "130481199211111115";
		IdcardNoServiceImpl im = new IdcardNoServiceImpl();
		System.out.println(im.calcCheckCode(idcardNo3.substring(0, 17)));
//		System.out.println(String.format(message, idcardNo, idcardNoService.validateIdcardNo(idcardNo)));
//		System.out.println(String.format(message, idcardNo, idcardNoService.validateIdcardNo(idcardNo)));
//		System.out.println(String.format(message, idcardNo1, idcardNoService.validateIdcardNo(idcardNo1)));
//		System.out.println(String.format(message, idcardNo2, idcardNoService.validateIdcardNo(idcardNo2)));
	}

	/**
	 * 测试邮箱验证
	 */
	@Test
	public void testEmail()
	{
		final String message = "邮箱地址[%s]是否合法:%s";

		final String email = null;
		final String email1 = "   ";
		final String email2 = "test@test.com";

		System.out.println(String.format(message, email, emailCheckService.validateEmail(email)));
		System.out.println(String.format(message, email1, emailCheckService.validateEmail(email1)));
		System.out.println(String.format(message, email2, emailCheckService.validateEmail(email2)));
	}

	/**
	 * 测试CurrencyService的金额转化为汉字数字大写格式方法, 小数转换
	 */
	@Test
	public void textCurrencyService()
	{
		final String string1 = "12.38";
		final String string2 = "120.3";
		final String string3 = "10200.35";
		final String string4 = "102001.35";
		final String string5 = "1020010.35";
		final String string6 = "-1020000.996";
		final String string7 = "0.00";
		final String string8 = "-.04";

		System.out.println("1:" + currencyService.convertToRMBCapital(string1));
		System.out.println("2:" + currencyService.convertToRMBCapital(string2));
		System.out.println("3:" + currencyService.convertToRMBCapital(string3));
		System.out.println("4:" + currencyService.convertToRMBCapital(string4));
		System.out.println("5:" + currencyService.convertToRMBCapital(string5));
		System.out.println("6:" + currencyService.convertToRMBCapital(string6));
		System.out.println("7:" + currencyService.convertToRMBCapital(string7));
		System.out.println("8:" + currencyService.convertToRMBCapital(string8));
	}

	/**
	 * 测试CurrencyService的金额转化为汉字数字大写格式方法, 整数转换
	 */
	@Test
	public void textCurrencyService2()
	{
		final String string1 = "123";
		final String string2 = "1203";
		final String string3 = "1020035";
		final String string4 = "10200135";
		final String string5 = "10200103500";
		final String string6 = "-102001030";

		System.out.println("1:" + currencyService.convertToRMBCapital(string1));
		System.out.println("2:" + currencyService.convertToRMBCapital(string2));
		System.out.println("3:" + currencyService.convertToRMBCapital(string3));
		System.out.println("4:" + currencyService.convertToRMBCapital(string4));
		System.out.println("5:" + currencyService.convertToRMBCapital(string5));
		System.out.println("6:" + currencyService.convertToRMBCapital(string6));
	}

	/**
	 * 测试HttpTransceiver的键值对功能
	 */
	@Test
	public void textHttpTransceiver()
	{
		final String url = "http://test.weatherplus.com.cn/airDelayService/flightDelay";

		final String key = "paramJson";
		final String val = "{\"flights\":[{\"arr_airport\":\"XIY\",\"arr_plan_time\":\"20180609234500\",\"carrier\":\"MU\",\"dep_airport\":\"KWL\",\"dep_plan_time\":\"20180609213500\",\"flight_number\":\"2308\",\"flight_order\":\"1\",\"flight_request_type\":\"prior\",\"plane_type\":\"A320\"}],\"header\":{\"appid\":\"tt99b1\",\"channel_id\":\"sale001\",\"customer_id\":\"testC002\",\"date\":\"201806081507\",\"key\":\"%2F5GBNC0Sua0v9r2sqcbw8bghYRo%3D\",\"product_id\":\"P20180001\"}}";
		final Map<String, String> kvpairs = new HashMap<>(2);
		kvpairs.put(key, val);

		final HttpResponseWrapper response = HttpTransceiver.doPostKVpair(url, null, kvpairs, false);
		if (response.getStatus())
		{
			System.out.println(response.getContent());
		}
		else
		{
			System.out.println(response.getErrorMessage());
		}
	}

	/**
	 * 测试值为null时，转化为json是否会转化该字段
	 */
	@Test
	public void textJson()
	{
		final HttpResponseWrapper response = new HttpResponseWrapper();
		response.setContent("Hello world");
		response.setErrorMessage(null);
		System.out.println(JSON.toJSONString(response));
	}

	/**
	 * 测试随机字符串的生成
	 */
	@Test
	public void testRandomGenerator()
	{
		System.out.println(RandomGenerator.getAlphanumericString(4));
		System.out.println(RandomGenerator.getNumbericString(4));
	}

	/**
	 * 测试AppNoProvider
	 */
	@Test
	public void testAppNoProvider()
	{
		System.out.println(AppNoProvider.provide(AppSystem.Midend.DYSUB));
	}

	/**
	 * 测试StringUtil
	 */
	@Test
	public void testStringUtil()
	{
		System.out.println(StringUtil.isNumber("100") == true);
		System.out.println(StringUtil.isNumber("100.1") == true);
		System.out.println(StringUtil.isNumber("-100") == true);
		System.out.println(StringUtil.isNumber("-100.1") == true);
		System.out.println(StringUtil.isNumber(null) == false);
		System.out.println(StringUtil.isNumber("") == false);
		System.out.println(StringUtil.isNumber("   ") == false);
		System.out.println(StringUtil.isNumber(".") == false);
		System.out.println(StringUtil.isNumber("-") == false);
		System.out.println(StringUtil.isNumber("100  .01") == false);
		System.out.println(StringUtil.isNumber("-100  .01") == false);
		System.out.println(StringUtil.isNumber("-100 ") == false);
		System.out.println(StringUtil.isNumber(".100") == false);
		System.out.println(StringUtil.isNumber("100.") == false);
		System.out.println(StringUtil.isNumber("100-") == false);
		System.out.println(StringUtil.isNumber("-.") == false);
	}
}
