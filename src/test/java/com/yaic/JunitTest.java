package com.yaic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import com.yaic.commons.codec.net.URLDecoder;
import com.yaic.commons.codec.net.URLEncoder;
import com.yaic.servicelayer.app.provider.AppNoProvider;
import com.yaic.servicelayer.charset.StandardCharset;
import com.yaic.servicelayer.codec.binary.Base64Decoder;
import com.yaic.servicelayer.codec.binary.Base64Encoder;
import com.yaic.servicelayer.constant.AppSystem;
import com.yaic.servicelayer.ftp.FtpFileUtil;
import com.yaic.servicelayer.http.HttpTransceiver;
import com.yaic.servicelayer.http.wrapper.HttpPostRawWrapper;
import com.yaic.servicelayer.http.wrapper.HttpResponseWrapper;
import com.yaic.servicelayer.id.IdGenerator;
import com.yaic.servicelayer.random.RandomGenerator;
import com.yaic.servicelayer.util.ExcelUtil;
import com.yaic.servicelayer.util.FileUtil;
import com.yaic.servicelayer.util.StringUtil;
import com.yaic.servicelayer.util.TimeUtil;
import junit.framework.TestCase;


/**
 * Unit test for simple App.
 */
public class JunitTest extends TestCase
{
	/**
	 * 第一个测试
	 */
	@Test
	public void testApp()
	{
		System.out.println("Hello world!");
	}

	/**
	 * 测试get方法
	 */
	@Test
	public void testDoGet()
	{
		final String url = "https://www.baidu.com";
		final HttpResponseWrapper httpResponseWrapper = HttpTransceiver.doGet(url);

		if (httpResponseWrapper.getStatus())
		{
			System.out.println(httpResponseWrapper.getStatusCode());
			System.out.println(httpResponseWrapper.getContent());
		}
		else
		{
			System.out.println(httpResponseWrapper.getStatusCode());
			System.out.println(httpResponseWrapper.getErrorMessage());
		}
	}

	/**
	 * 测试post方法
	 */
	@Test
	public void testDoPost()
	{
		final String url = "http://183.60.22.143/sns/dy-order-service?access_token=57dff07376c34bd288b10899869912c1&open_id=41b4dee55a974388b4698b4fc49ae08d";
		final String raw = "{\"data\":{\"createOrderReq\":{\"orderList\":[{\"premium\":418,\"startDate\":\"2018-5-23 00:00:00\",\"uwCount\":1,\"customerList\":[{\"customerType\":\"1\",\"docType\":\"03\",\"sex\":\"02\",\"customerFlag\":1,\"docNo\":\"110101198001010010\",\"birthDate\":\"1981-07-12\",\"email\":\"1577936562@qq.com\",\"customerName\":\"测试1\",\"phoneNo\":\"13822592982\"}],\"itemAcciList\":[{\"nominativeInd\":\"1\",\"quantity\":\"1\",\"occupationCode\":\"0000\",\"acciInsuredList\":[{\"acciBenefitList\":[{\"insuredRelation\":\"08\",\"docType\":\"02\",\"sex\":\"02\",\"customerFlag\":\"3\",\"docNo\":\"110101201803290071\",\"birthDate\":\"2015-07-17\",\"customerName\":\"测试3\",\"benifitPercent\":\"100\",\"phoneNo\":\"13822592982\"}],\"docType\":\"02\",\"sex\":\"02\",\"customerFlag\":\"2\",\"docNo\":\"1101215666\",\"birthDate\":\"2018-01-28\",\"appliRelation\":\"04\",\"customerName\":\"测试\",\"phoneNo\":\"13822592982\"}]}],\"riskDynamicList\":[{\"fieldAa\":\"N\",\"fieldAb\":\"\"}]}]}},\"agrtCode\":\"10009002003000\",\"requestTime\":\"2016-07-19 09:17:13\",\"dataSource\":\"O-XHDS\",\"outBusinessCode\":\"NO201805qew01Sr444ets016\",\"interfaceCode\":\"CreateOrder\"}";
		final HttpPostRawWrapper wrapper = new HttpPostRawWrapper();
		wrapper.setServerUrl(url);
		wrapper.setRawBody(raw);

		final HttpResponseWrapper httpResponseWrapper = HttpTransceiver.doPost(wrapper);

		if (httpResponseWrapper.getStatus())
		{
			System.out.println(httpResponseWrapper.getHeaders());
			System.out.println(httpResponseWrapper.getStatusCode());
			System.out.println(httpResponseWrapper.getContent());
		}
		else
		{
			System.out.println(httpResponseWrapper.getStatusCode());
			System.out.println(httpResponseWrapper.getErrorMessage());
		}
	}

	/**
	 * 测试Base64编码
	 */
	@Test
	public void testCodec()
	{
		final Charset charset = StandardCharset.UTF_8;
		final String string = "Hello world";
		final String encode = Base64Encoder.encode(string.getBytes(charset));
		System.out.println("Base64编码后的字符串为" + encode);

		final byte[] bs = Base64Decoder.decode(encode);
		System.out.println("Base64解码后的字符串为" + new String(bs, charset));
	}

	/**
	 * 测试系统编号获取
	 */
	@Test
	public void testAppNo()
	{
		System.out.println("订单系统的编号为" + AppNoProvider.provide(AppSystem.Midend.ORDER));
		System.out.println("电子保单系统的编号为" + AppNoProvider.provide(AppSystem.Backend.EPOLICY));
	}

	/**
	 * 测试ID生成
	 */
	@Test
	public void testId()
	{
		List<String> idStrings = new ArrayList<>(100);
		for (int i = 0; i < 100; i++) {
			idStrings.add(IdGenerator.generate());
		}
		System.out.println(idStrings.size());
	}

	/**
	 * 测试随机字符串生成
	 */
	@Test
	public void testRandom()
	{
		for (int i = 0; i < 10; i++)
		{
			System.out.println(RandomGenerator.getNumbericString(6));
			System.out.println(RandomGenerator.getAlphanumericString(6));
		}
	}

	/**
	 * 测试URLCodec
	 */
	@Test
	public void testURLCodec()
	{
		final String s = "我爱你，中国!";
		final String encode = URLEncoder.encode(s, StandardCharset.UTF_8);
		System.out.println("编码前: " + s);
		System.out.println("编码后: " + encode);
		System.out.println("解码后: " + URLDecoder.decode(encode, StandardCharset.UTF_8));
	}

	/**
	 * 测试URLCodec
	 */
	@Test
	public void testCurrency()
	{
		final String string = "123.23";

		final int integerPartLength = string.indexOf('.');
		final int decimalPartLength = string.length() - integerPartLength - 1;
		System.out.println(integerPartLength);
		System.out.println(decimalPartLength);

		final BigDecimal bigDecimal = BigDecimal.valueOf(Math.pow(10, 4) * Math.pow(10, 4) * Math.pow(10, 4));
		System.out.println(bigDecimal);

		final double bigDecimal1 = (Math.pow(10, 8) * Math.pow(10, 4) * Math.pow(10, 4));
		System.out.println(bigDecimal1);

		System.out.println("monetary_unit".toUpperCase());
	}

	/**
	 * 测试String.isDecimal方法
	 */
	@Test
	public void testStringUtil()
	{
		final String string1 = "09.9";
		final String string2 = "0.9";
		final String string3 = "-0.00";
		System.out.println(StringUtil.isDecimal(string1));
		System.out.println(StringUtil.isDecimal(string2));
		System.out.println(StringUtil.isDecimal(string3));
	}

	/**
	 * 测试double类
	 */
	@Test
	public void testDouble()
	{
		final String string1 = "09.9";
		final String string2 = "0.90";
		final String string3 = "-0.00";

		System.out.println(Double.parseDouble(string1));
		System.out.println(Double.parseDouble(string2));
		System.out.println(Double.parseDouble(string3));
	}

	/**
	 * 测试TimeUtil类
	 */
	@Test
	public void testTimeUtil()
	{
		// DATETIME_NO_SEC_FORMAT = "yyyyMMddHHmm";
		final String dateStr = "201806071129";
		System.out.println(TimeUtil.parseDate(dateStr, TimeUtil.DATETIME_NO_SEC_FORMAT));

		final Date date = new Date();
		System.out.println(TimeUtil.format(date, TimeUtil.DATETIME_NO_SEC_FORMAT));
	}
	
	
	/**
	 * 测试ExcelUtil
	 * @throws IOException 
	 */
	public void testExcelUtil() throws IOException {
		String[] titleArr = {"产品代码","保单号","保单状态","保费"};
		String sheetName = "对账文件";
			List<String> dataList = FileUtil.readFileToList("E:/test.txt",StandardCharset.UTF_8.name());
			HSSFWorkbook workbook = ExcelUtil.createExcel(titleArr, dataList, sheetName);
			String excelStr = ExcelUtil.toString(workbook);
//			byte[] excelobj = ExcelUtil.toByte(workbook);
			System.out.println(excelStr);
	}
	
	
	
	/**
	 * 测试FtpFileUtil
	 */
	@Test
	public void testFtpFileUtil() {
		final FtpFileUtil ftpFileUtil = new FtpFileUtil("192.168.144.105", 21, "hbw1", "hbw@HBW123~");
		for (int i = 0; i < 10; i++) {
			final String fileName = i + ".txt";
			final String filenamePath = "E:/" + i + ".txt";
			final String filePath = "/SIT";
			new Thread(new Runnable() {
				@Override
				public void run() {
					InputStream input = null;
					System.out.println("开始执行");
					try {
						input = new FileInputStream(new File(filenamePath));
						System.out.println("读完文件，开始上传");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					System.out.println("==" + Thread.currentThread().getName() + "上传完毕结果：" + ftpFileUtil.uploadFile(filePath, fileName, input));
				}
			}).start();
		}

		try {
			Thread.sleep(500 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

	
