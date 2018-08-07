package com.yaic.servicelayer.http.wrapper;

import java.util.Map;


/**
 * 以 application/x-www-form-urlencoded方式提交数据
 *
 * @author Qu Dihuai
 */
public class HttpPostKVpairWrapper extends HttpPostWrapper
{
	/**
	 * <p>
	 * 键值对
	 */
	private Map<String, String> kvpairs;

	/**
	 * @return the kvpairs
	 */
	public Map<String, String> getKvpairs()
	{
		return kvpairs;
	}

	/**
	 * @param kvpairs
	 *           the kvpairs to set
	 */
	public void setKvpairs(final Map<String, String> kvpairs)
	{
		this.kvpairs = kvpairs;
	}

}
