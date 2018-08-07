package com.yaic.servicelayer.phone;

/**
 * @author Qu Dihuai
 */
public interface PhoneService
{

	/**
	 * 手机号码校验, 正则表达式为^1\\d{10}$
	 *
	 * @param phoneNo
	 * @return boolean
	 */
	boolean validateMobilePhone(String phoneNo);

	/**
	 * 固定电话号码(即座机号码)校验
	 *
	 * @param phoneNo
	 * @return boolean
	 */
	boolean validateTelePhone(String phoneNo);

}
