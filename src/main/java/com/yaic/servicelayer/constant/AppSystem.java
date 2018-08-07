package com.yaic.servicelayer.constant;

/**
 * 用于定义各个系统的系统编号对应的键值
 *
 * @author Qu Dihuai
 *
 */
public interface AppSystem
{
	/**
	 * 前台系统
	 */
	interface Frontend
	{
		String WEBSITE = "system.frontend.website"; //官网
		String WechatServer = "system.frontend.wechatserver"; //微信
		String OWSS = "system.frontend.owss"; //官网官微重构
		String IAPS = "system.frontend.iaps"; //E连保
		String WebChat = "system.frontend.webchat"; //在线客服
		String ACTI = "system.frontend.acti"; // H5激活页面
	}

	/**
	 * 中台系统
	 */
	interface Midend
	{
		String OPEN = "system.midend.open"; //开放平台
		String DYSUB = "system.midend.dysub"; //动态标的
		String ORDER = "system.midend.order"; //订单管理
		String ENPAY = "system.midend.enpay"; //易安支付
		String OUTERPAY = "system.midend.outerpay"; //资金回调服务系统
		String USER = "system.midend.user"; //用户中心
		String TRANS = "system.midend.trans"; //订单转换
		String TASK = "system.midend.task"; //任务管理系统
		String WSPRDT = "system.midend.wsprdt"; //微信航延险产品
		String MAILSERVER = "system.midend.mailserver"; //邮件服务
		String PUBS = "system.midend.pubs"; //公共服务系统
		String ICS = "system.midend.ics"; //理赔代理
		String QUOTMS = "system.midend.quotms"; //报价系统
		String CGI = "system.midend.cgi"; //贷款保证保险管理系统
		String PDMS = "system.midend.pdms"; //产品工厂
		String SKYEYE = "system.midend.skyeye"; //业务监控系统
	}

	/**
	 * 后台系统
	 */
	interface Backend
	{
		String CMS = "system.backend.cms"; //资金
		String CLM = "system.backend.clm"; //理赔
		String FIN = "system.backend.fin"; //收付
		String NBZ = "system.backend.nbz"; //承保
		String NCLM = "system.backend.nclm"; //新理赔
		String NFIN = "system.backend.nfin"; //新收付
		String NSALES = "system.backend.nsales"; //新销管
		String SALES = "system.backend.sales"; //销管
		String CEM = "system.backend.cem"; //费控
		String CEMAPPSER = "system.backend.cemappser"; //费控APP
		String CEMBUSSINESS = "system.backend.cembussiness"; //费控商旅
		String ECIF = "system.backend.ecif"; //客户关系管理系统
		String EIS = "system.backend.eis"; //电子发票
		String EXWEB = "system.backend.exweb"; //外网查询
		String NECIF = "system.backend.necif"; //新客户关系管理系统
		String NPCS = "system.backend.npcs"; //新客服
		String PRINT = "system.backend.print"; //打印系统
		String QRY = "system.backend.qry"; //综合查询
		String REINS = "system.backend.reins"; //再保
		String SSO = "system.backend.sso"; //单点
		String TIMESEVER = "system.backend.timesever"; //时间服务器
		String UM = "system.backend.um"; //用户管理
		String UPROCESS = "system.backend.uprocess"; //提供统一的流程配置和管理功能
		String VC = "system.backend.vc"; //单证
		String AML = "system.backend.aml"; //反洗钱
		String EPOLICY = "system.backend.epolicy"; //电子保单
	}

	/**
	 * 其他系统
	 */
	interface Others
	{
		String TMANAGE = "system.others.tmanage"; //电销管理系统
		String ITM = "system.others.itm"; //电话营销系统
		String TMDATASVR = "system.others.tmdatasvr"; //电销转数系统
		String CEMAPP_ANDROID = "system.others.cemapp.android"; //费控安卓App
		String CEMAPP_IOS = "system.others.cemapp.ios"; //费控安卓IOS
		String ISSUEPLAT = "system.others.issueplat"; //可视化大屏
		String PICTUREPF = "system.others.picturepf"; //影像系统
		String SMS = "system.others.sms"; //短信系统
	}
}

