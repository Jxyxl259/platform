package com.yaic.servicelayer.email;

/**
 * @author Qu Dihuai
 */
public interface EmailCheckService
{

	/**
	 * 邮箱地址校验
	 *
	 * @param email
	 * @return boolean
	 */
	boolean validateEmail(String email);

}
