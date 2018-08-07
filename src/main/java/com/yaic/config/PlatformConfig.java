/**
 *
 */
package com.yaic.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * 配置Spring扫描的包路径
 *
 * @author Qu Dihuai
 */
@Configuration
@ComponentScan("com.yaic.servicelayer")
public class PlatformConfig
{
	/**
	 * 默认的构造方法
	 */
	public PlatformConfig()
	{
		super();
	}
}
