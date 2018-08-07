package com.yaic.servicelayer.idcard;

import java.util.Date;


/**
 * @author Qu Dihuai
 */
public interface IdcardNoService
{

	/**
	 * 验证是否是合法的18位或15的身份证号码
	 *
	 * @param idcardNo
	 * @return boolean
	 */
	boolean validateIdcardNo(String idcardNo);

	/**
	 * 计算18位身份证号码的最后一位, 即校验码
	 *
	 * @param str
	 * @return idcardNo
	 * @throws IllegalArgumentException
	 *            if idcardNo is null, or the length of ID card number is not 18
	 */
	String calculateCheckCode(String str);

	/**
	 * 将15位的身份证号码转化为18位
	 *
	 * @param idcardNo
	 * @return idcardNo if idcardNo is null, will return null
	 * @throws IllegalArgumentException
	 *            if the length of ID card number is not 15, or idcardNo is invalid
	 */
	String convert15To18(String idcardNo);

	/**
	 * 获取15位或18位身份证号码的地址码。地址码表示编码对象常住户口所在县（市、旗、区）的行政区划代码，按GB/T2260的规定执行。
	 *
	 * @param idcardNo
	 * @return addressCode
	 * @throws IllegalArgumentException
	 *            if idcardNo is null, or the length of ID card number is not 18 and 15
	 */
	String getAddressCode(String idcardNo);

	/**
	 * 获取15位或18位身份证号码的出生日期(年月日)
	 *
	 * @param idcardNo
	 * @return Date birthDate
	 * @throws IllegalArgumentException
	 *            if idcardNo is null, or the length of ID card number is not 18 and 15
	 */
	Date getBirthDate(String idcardNo);

	/**
	 * 获取15位或18位身份证号码的出生日期码。出生日期码表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。
	 *
	 * @param idcardNo
	 * @return String the birthDate code
	 * @throws IllegalArgumentException
	 *            if idcardNo is null, or the length of ID card number is not 18 and 15
	 */
	String getBirthDateCode(String idcardNo);

	/**
	 * 获取18位身份证号码的出生天数
	 *
	 * @param idcardNo
	 * @return int the day of birthdate
	 * @throws IllegalArgumentException
	 *            if idcardNo is null, or the length of ID card number is not 18 and 15
	 */
	int getDayOfBirthdate(String idcardNo);

	/**
	 * 获取15位或18位身份证号码的出生月份
	 *
	 * @param idcardNo
	 * @return int the month of birthDate
	 * @throws IllegalArgumentException
	 *            if idcardNo is null, or the length of ID card number is not 18 and 15
	 */
	int getMonthOfBirthdate(String idcardNo);

	/**
	 * 获取15位或18位身份证号码的出生年份
	 *
	 * @param idcardNo
	 * @return int the year of birthDate
	 * @throws IllegalArgumentException
	 *            if idcardNo is null, or the length of ID card number is not 18 and 15
	 */
	int getBirthYear(String idcardNo);

	/**
	 * @param idcardNo
	 * @return 获取18位身份证号码的校验码
	 *         <p>
	 *         校验码是根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的
	 * @throws IllegalArgumentException
	 *            if idcardNo is null, or the length of ID card number is not 18
	 */
	String getCheckCode(String idcardNo);

	/**
	 * 获取15位或18位身份证号码的省份、自治区、直辖市代码
	 *
	 * @param idcardNo
	 * @return the province code
	 *
	 * @throws IllegalArgumentException
	 *            if idcardNo is null, or the length of ID card number is not 18 and 15
	 */
	int getProvinceCode(String idcardNo);

	/**
	 * 获取18位身份证号码的顺序码.顺序码表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配给女性.
	 *
	 * @param idcardNo
	 * @return the sequence code
	 * @throws IllegalArgumentException
	 *            if idcardNo is null, or the length of ID card number is not 18 and 15
	 */
	String getSequenceCode(String idcardNo);

	/**
	 * 返回18位或15位身份证号码的性别, 字符串01表示男性，02表示女性
	 *
	 * @param idcardNo
	 * @return sex if sex is male, return 01, else return 02
	 * @throws IllegalArgumentException
	 *            if idcardNo is null, or the length of ID card number is not 18 and 15
	 */
	String getSex(String idcardNo);

}
