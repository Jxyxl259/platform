package com.yaic.servicelayer.enums;

/**
 * @author Qu Dihuai
 *
 */
public enum HttpPostType
{
	/**
	 * 以multipart/form-data方式提交数据;
	 *
	 * 将表单的数据组织成Key-Value形式，用分隔符boundary（boundary可任意设置）处理成一条消息。 由于有boundary隔离，所以既可以上传文件，也可以上传参数。
	 */
	MULTIPART_FORM_DATA,

	/**
	 * 以 application/x-www-form-urlencoded方式提交数据;
	 *
	 * 将表单内的数据转换为Key-Value
	 */
	APPLICATION_FORM_URLENCODED,

	/**
	 * 以text/plain、application/json、application/javascript、application/xml、text/xml、text/html方式提交数据;
	 *
	 * 可以上传任意格式的【文本】，可以上传text、json、xml、html等
	 */
	RAW,

	/**
	 * 即Content-Type:application/octet-stream，只可以上传二进制数据，通常用来上传文件
	 */
	BINARY;
}
