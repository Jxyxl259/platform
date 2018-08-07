package com.yaic.servicelayer.http.service;

import com.yaic.servicelayer.http.wrapper.HttpGetWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostKVpairWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostMultipartWrapper;
import com.yaic.servicelayer.http.wrapper.HttpPostRawWrapper;
import com.yaic.servicelayer.http.wrapper.HttpResponseWrapper;


/**
 * 用于向服务器发送请求，及接收和解析服务器的响应结果
 *
 * @author Qu Dihuai
 * @since 2018.3.5
 */
public interface HttpTransceiverService
{

	/**
	 * 执行get方法, 参数为HttpGetWrapper对象
	 *
	 * @param requestWrapper
	 * @return responseWrapper
	 */
	public HttpResponseWrapper doGet(final HttpGetWrapper requestWrapper);

	/**
	 * 执行post方法, 参数为HttpPostRawWrapper对象
	 *
	 * @param requestWrapper
	 * @return responseWrapper
	 */
	public HttpResponseWrapper doPost(final HttpPostRawWrapper requestWrapper);

	/**
	 * 执行post方法, 参数为HttpPostKVpairWrapper对象
	 *
	 * @param requestWrapper
	 * @return responseWrapper
	 */
	public HttpResponseWrapper doPost(final HttpPostKVpairWrapper requestWrapper);

	/**
	 * 执行post方法, 参数为HttpPostMultipartWrapper对象
	 *
	 * @param requestWrapper
	 * @return responseWrapper
	 */
	public HttpResponseWrapper doPost(final HttpPostMultipartWrapper requestWrapper);

}
