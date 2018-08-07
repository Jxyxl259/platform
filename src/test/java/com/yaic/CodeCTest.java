package com.yaic;

import java.nio.charset.Charset;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yaic.config.PlatformConfig;
import com.yaic.servicelayer.codec.digest.AESHelper;
import com.yaic.servicelayer.codec.digest.MD5Helper;
import com.yaic.util.DiDiAESUtils;
import com.yaic.util.DigestUtils;
import com.yaic.util.MD5;
import com.yaic.util.YiLongAESUtil;


/**
 * test CodeCTest
 * 
 * @author wangwf
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
{ PlatformConfig.class })
public class CodeCTest
{

	private static final String content = "{\"content\":\"{\"UUID\":\"d92ad782-8537-4895-8235-117517f0a874\",\"bizContent\":{\"errorCode\":1,\"errorMsg\":\"保费不一致,请检查,\",\"insResults\":[{\"errorCode\":1,\"errorMsg\":\"失败\",\"insurantKey\":\"130101194607120017|1|压测_2017071303\"}],\"orderId\":\"101000568579000056988899041529561962724672648\"},\"timestamp\":1529563135445}\",\"inscompanyId\":4\"}";
	private static final String aesKey = "test123";

	@Ignore
	@Test
	public void testAESHelper() throws Exception
	{

		/* ------------------ 艺龙网渠道 --------------- */
		final String YiLongAESHelperStr = AESHelper.encrypt(content, aesKey);
		final String YiLongAESUtilStr = YiLongAESUtil.encrypt(content, aesKey);

		Assert.assertTrue(YiLongAESHelperStr.equals(YiLongAESUtilStr));

		System.out.println(" 艺龙网 YLAESHelperStr decode : " + AESHelper.decrypt(YiLongAESHelperStr, aesKey));
		System.out.println(" 艺龙网 YiLongAESUtil decode : " + YiLongAESUtil.decrypt(YiLongAESHelperStr, aesKey));
		System.out.println(AESHelper.decrypt(YiLongAESHelperStr, aesKey).equals(YiLongAESUtil.decrypt(YiLongAESHelperStr, aesKey)));

		/* ------------------ 滴滴渠道 --------------- */
		final String DiDiAESHelperStr = AESHelper.encryptBase64(aesKey, content);
		final String DiDiAESUtilStr = DiDiAESUtils.encryptBase64(aesKey, content);

		Assert.assertTrue(DiDiAESHelperStr.equals(DiDiAESUtilStr));

		System.out.println(" 滴滴 DiDiAESHelperStr decode : " + AESHelper.decryptBase64(aesKey, DiDiAESHelperStr));
		System.out.println(" 滴滴 DiDiAESUtilStr decode : " + DiDiAESUtils.decryptBase64(aesKey, DiDiAESHelperStr));
		System.out.println(
				AESHelper.decryptBase64(aesKey, DiDiAESHelperStr).equals(DiDiAESUtils.decryptBase64(aesKey, DiDiAESHelperStr)));

		System.out.println(DigestUtils.sha1Hex(content).equals(org.apache.commons.codec.digest.DigestUtils.shaHex(content)));
	}


	//@Ignore
	@Test
	public void MD5Test() throws Exception
	{
		final String content1 = "12131231231231sjdaksjbkbk1nnfgn我的fgmfmfbmfbmfv1";
		final String key1 = "123132jkn1jkj1";


		final String MD5Str = MD5.getMd5Code(content1 + key1, "GBK");
		final String MD5HelperStr = MD5Helper.sign(content1 + key1, Charset.forName("GBK"));
		System.out.println(MD5HelperStr.equals(MD5Str));
		/*
		 * String MD5HelperStr = MD5Helper.sign(content1 + key1); String MD5McryptStr = MD5Mcrypt.getMd5Code(content1 +
		 * key1); String MD5Str = MD5.getMd5Code(content1 + key1); String MD5Str1 = MD5.getMD5(content1 + key1);
		 * 
		 * Mcrypt mcrypt = new MD5Mcrypt(StandardCharset.UTF_8.name(), key1);
		 * 
		 * String serverToken = MD5.sign(content1, key1, StandardCharset.UTF_8.name());
		 * 
		 * System.out.println(MD5HelperStr); System.out.println(MD5McryptStr); System.out.println(MD5Str);
		 * System.out.println(MD5Str1); System.out.println(mcrypt.encode(content1)); System.out.println(serverToken);
		 * 
		 * System.out.println(MD5HelperStr.equals(MD5Str)); System.out.println(MD5HelperStr.equals(MD5McryptStr));
		 * System.out.println(MD5McryptStr.equals(MD5Str));
		 * System.out.println(MD5HelperStr.toUpperCase().equals(MD5Str1));
		 * System.out.println(MD5HelperStr.equals(mcrypt.encode(content1))); System.out.println(MD5Helper.sign(content1 +
		 * key1).equals(serverToken));
		 */
	}

	@Ignore
	@Test
	public void RSATest() throws Exception
	{
		/*
		 * String privateKey =
		 * "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCp+4/uQdhlsVptR2uwh9FkC1KOP0ELwbkMDcKtxM2I5yHzX/aSSpIMA4SMBMWu8EL5eI7dMRzlFzb+hn28sXinu0jfR+QvPHwLLUE5h62fIIF0bj1mIVOZTfLmC+mA8VpIHC2kjPCmjfa0ap30saUguMoUnw1YrQzn2KVdosvlyRgywSu0Gwroi0NlQZ9B1winx4uFFrnnv4OU69Fu7wJElEvS1bQBJb2RaoJZYutAdUuymXyJ5va/q994r3hgfmy2f6nApTNPbeQR+PClTVH1PIPvJI6J6XAc4QJ1tFusqTs4DDvzVTlfeTSw541Tk3DrEw0nO6/x+0FjuEkGJECTAgMBAAECggEBAIcqO3Q4tat/kKlO3oocJdvIyRfFoqKHo+66znBBCzLun+eYCkiftWyKK47viIYoFQms3OV0VUax5BAWv8sY0BmIalTqJL+O+BAnJzNo+R2MyoPb2UTqAUDpY9mb5UycHq8ygPTVAdNfFaq3EO1viR/w8Pfe1c0KpjWB51UCy+Hmbzz4twcON5r1ilNFdBt6w/Sntfsjf/S1vdcLx8ZNiCAPd1LHHZxtHlZs0IdFrPBtgl55c4Cl0ntFT/g7RQOjKVuObnPN84FXf23KbxRhMKVoRhKT8Q5PVjNqqMAYjDjfKrHF6Yw0IAR9Z6+A/xnos5DDUG+OM6c8ea9vJ6B7VykCgYEA1+Q8DVEJcSi+nyrkcjKccn/jRW7GeKXklxkACtXiWAJ/1THVybRM3cfbYSEHsIppTJLaBi1f/cvOgNXktrAcYbewNe4ob8EKWWqYrIuEtcRMDXgLu5ALnYGFTKfphSBh1ZfgnuEy/Pa3tj/wHqGHq5RK2GTPjDTChhovsw42Ni0CgYEAyY/owceOPp+QQICStuRZYBZ+XOVsQ9SPhUaKL9c6qaanTtUULAzONeOl6ysmnI+NE2rPbsQxKrrBEC/lCWfV9L0X4X7Ouc2790pIIw5P1WwxbowSpqo5hl9dH8aBeshAo7+JuUl/hDh4QktVjRHAF80bkEw8meyqOmVlwG2bSb8CgYAyXxsv1DeKwoHvazeP+YUNJg+l9Jm0LqiuJHQhExRTiom++XizLjE9EdN6zxUXOMQmzKC4DkA2XCYbY0yQ33hPyGcBvkaLBJRgloF2yLq3GkzQW7EJGyvKnRy37PmMSSjqiBwtlceqw/nLORHSY8fe3aO055iRUwIL/fIhKfC2JQKBgQClzYFz1cnG7c7loF4PoGt8xUQQ+pBCg9nDkjEeBXg2EebSzCiZy7bdUXQsrQRICTXNYTFdNnoTYihqPluzjvzLI7k/PuaipQAX/by1SZKWRzeqbgLxollLlaqu9sWP0KaLjIWoKzN/+kvCjOHE93MCoTApVO0M2Ud2Xe6DiiYRVQKBgQC6wTn8Epni+ekrWG0s6kkcWxGuN99PLPM7VfwtEr80GkC+sCJ6cM+ZeZdQadiCPzCFJUNwogD9fsnjzrPex4h68+Eup4nTBvdEJRwYa2PU5dviYrbQ8jrSACebAfX4xYIUgfQQM/bHA0YblGYguNFr1aamu6JqQv+JALfhSPCq5Q==";
		 * String publicKey =
		 * "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqfuP7kHYZbFabUdrsIfRZAtSjj9BC8G5DA3CrcTNiOch81/2kkqSDAOEjATFrvBC+XiO3TEc5Rc2/oZ9vLF4p7tI30fkLzx8Cy1BOYetnyCBdG49ZiFTmU3y5gvpgPFaSBwtpIzwpo32tGqd9LGlILjKFJ8NWK0M59ilXaLL5ckYMsErtBsK6ItDZUGfQdcIp8eLhRa557+DlOvRbu8CRJRL0tW0ASW9kWqCWWLrQHVLspl8ieb2v6vfeK94YH5stn+pwKUzT23kEfjwpU1R9TyD7ySOielwHOECdbRbrKk7OAw781U5X3k0sOeNU5Nw6xMNJzuv8ftBY7hJBiRAkwIDAQAB";
		 * publicKey =
		 * "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGg23jgZdoS1fbp1J1jZSi+D2jEi/NFyU/6ONvGFDwrEhiKfNET+1cLnpj7qWIi3uKJHjcpQXxtx/RtxiMlmaDkxyPSPQDc0YcojqGvLRbPZH3+QvmAEIFb5FcLzG+/xL2iNQQFq2do0XXWdxdCXsifXz5cd4q8Yv2HrvGbEH9kQIDAQAB";
		 * privateKey =
		 * "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIaDbeOBl2hLV9unUnWNlKL4PaMSL80XJT/o428YUPCsSGIp80RP7VwuemPupYiLe4okeNylBfG3H9G3GIyWZoOTHI9I9ANzRhyiOoa8tFs9kff5C+YAQgVvkVwvMb7/EvaI1BAWrZ2jRddZ3F0JeyJ9fPlx3irxi/Yeu8ZsQf2RAgMBAAECgYBY4nw4u6d7CV5TDSvV9epkDjlcimX/1e2NX6z5+k1x28Yw55uSZmZoZWYGpunHYrQNwrACzSEyalmiUKTuMWwnS5f2TXjo1blRsQpyCdbGfNzEM4Z0Ix1ICU91f9vkklC3K6Lf4RIhm1tqUIQHCSGPnRRKi8z/WWINweB+PytbaQJBAMg96ZZiMMHluR3PYYXoizfmbePYupNSYM3MtArG38oHmL0aUmhATg1LpHIkEgP8xehN5pXdLmF5GpgQzt4XW8cCQQCr+B1A4sWvGwoWT8TkRJS7lKrvz+s65LlLc93VdM/3YXOYVNX6RT1Aa9SDEey3Mv+aqI467qF3LaQvCRN7nmvnAkBjHsqXB2ntQULeL76to4vukuEIrUrGDDwhA1iIESPMBPClgvcR5er9B9mSKqrEUIbC1HT08DS6SVFRvtg39cYXAkANNjuxH3sGj/I/cpNy4h33J13WnMe6t5XQyS8S1i2gGNmTq3jhCG7DQ0WJaFgiXwWqMSb0Oh8nk9cYUf8wMSSjAkBKHTvjHJBfs8P6gnzfNqZeXwoS1PBEkvqjzjv2PpBhvMOjeZFWc2/Er51lO/wjsup/u7ZdtA6mDTCFJ4x+mBFO";
		 * 
		 * String data =
		 * "<head><version>3.0</version><function>ant.bxcloud.core.issue</function><transTime>20170811152255</transTime><transTimeZone>UTC+8</transTimeZone><reqMsgId>123412431</reqMsgId><format>XML</format><signType>RSA</signType><asyn>0</asyn><cid>yian</cid></head><body><policy><policyNo>alitrip#test10000</policyNo><prodNo>1213</prodNo><outProdNo></outProdNo><summaryPolicyNo></summaryPolicyNo><policyType>0</policyType><premium>6</premium><actualPremium>6</actualPremium><sumInsured>30</sumInsured><insuredTime>20170811152256</insuredTime><issueTime>20170811152256</issueTime><effectStartTime>20170811152256</effectStartTime><effectEndTime>20170811152256</effectEndTime><applyNum>1</applyNum></policy></body>";
		 * data = "123141234123";
		 * 
		 * String sign = AliRSAUtil.sign(data, privateKey); boolean verify = com.yaic.util.AliRSAUtil.verify(data,
		 * publicKey, sign);
		 * 
		 * System.out.println(" 使用调整前AliRSAUtil签名结果  : "+ sign); System.out.println(" 使用调整后AliRSAUtil验签结果  : "+ verify);
		 * 
		 * sign = com.yaic.util.AliRSAUtil.sign(data, privateKey); verify = AliRSAUtil.verify(data, publicKey, sign);
		 * 
		 * System.out.println(" 使用调整后AliRSAUtil签名结果  : "+ sign); System.out.println(" 使用调整前AliRSAUtil验签结果  : "+ verify);
		 * 
		 * System.out.println(" -------------------------------------------- "); sign =
		 * com.yaic.util.RSACoder.encryptByPublicKey(data, publicKey);
		 * 
		 * System.out.println(" 使用调整后RSACoder 公钥加密  : "+ sign); System.out.println(" 使用调整前RSACoder 私钥解密  : "+
		 * RSACoder.decryptByPrivateKey(sign, privateKey));
		 * 
		 * sign = RSACoder.encryptByPrivateKey(data, privateKey);
		 * 
		 * System.out.println(" 使用调整前RSACoder 私钥加密  : "+ sign); System.out.println(" 使用调整后RSACoder 公钥解密  : "+
		 * com.yaic.util.RSACoder.decryptByPublicKey(sign, publicKey));
		 * 
		 * sign = RSACoder.sign(data, privateKey);
		 * 
		 * System.out.println(" 使用调整前RSACoder 私钥签名  : "+ sign); System.out.println(" 使用调整后RSACoder 公钥验签  : "+
		 * com.yaic.util.RSACoder.verify(data, publicKey, sign));
		 * 
		 */}

}
