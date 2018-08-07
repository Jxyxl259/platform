package com.yaic.servicelayer.app.tool;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yaic.servicelayer.constant.AppSystem;


/**
 * 用来修改和转换为systemNo.json
 *
 */
public class PrepareDataTool
{
	/**
	 * @param args
	 */
	public static void main(final String[] args)
	{
		final Map<String, String> map = new HashMap<>();
		// 前台
		map.put(AppSystem.Frontend.WEBSITE, "100");
		map.put(AppSystem.Frontend.WechatServer, "101");
		map.put(AppSystem.Frontend.OWSS, "102");
		map.put(AppSystem.Frontend.IAPS, "103");
		map.put(AppSystem.Frontend.WebChat, "104");
		map.put(AppSystem.Frontend.ACTI, "105");

		// 中台
		map.put(AppSystem.Midend.OPEN, "200");
		map.put(AppSystem.Midend.DYSUB, "201");
		map.put(AppSystem.Midend.ORDER, "202");
		map.put(AppSystem.Midend.ENPAY, "203");
		map.put(AppSystem.Midend.OUTERPAY, "205");
		map.put(AppSystem.Midend.USER, "206");
		map.put(AppSystem.Midend.TRANS, "207");
		map.put(AppSystem.Midend.TASK, "208");
		map.put(AppSystem.Midend.WSPRDT, "209");
		map.put(AppSystem.Midend.MAILSERVER, "210");
		map.put(AppSystem.Midend.PUBS, "211");
		map.put(AppSystem.Midend.ICS, "212");
		map.put(AppSystem.Midend.QUOTMS, "213");
		map.put(AppSystem.Midend.CGI, "214");
		map.put(AppSystem.Midend.PDMS, "215");
		map.put(AppSystem.Midend.SKYEYE, "216");

		// 后台
		map.put(AppSystem.Backend.CMS, "300");
		map.put(AppSystem.Backend.CLM, "301");
		map.put(AppSystem.Backend.FIN, "302");
		map.put(AppSystem.Backend.NBZ, "303");
		map.put(AppSystem.Backend.NCLM, "304");
		map.put(AppSystem.Backend.NFIN, "305");
		map.put(AppSystem.Backend.NSALES, "306");
		map.put(AppSystem.Backend.SALES, "307");
		map.put(AppSystem.Backend.CEM, "308");
		map.put(AppSystem.Backend.CEMAPPSER, "309");
		map.put(AppSystem.Backend.CEMBUSSINESS, "310");
		map.put(AppSystem.Backend.ECIF, "311");
		map.put(AppSystem.Backend.EIS, "312");
		map.put(AppSystem.Backend.EXWEB, "313");
		map.put(AppSystem.Backend.NECIF, "314");
		map.put(AppSystem.Backend.NPCS, "315");
		map.put(AppSystem.Backend.PRINT, "316");
		map.put(AppSystem.Backend.QRY, "317");
		map.put(AppSystem.Backend.REINS, "318");
		map.put(AppSystem.Backend.SSO, "319");
		map.put(AppSystem.Backend.TIMESEVER, "320");
		map.put(AppSystem.Backend.UM, "321");
		map.put(AppSystem.Backend.UPROCESS, "322");
		map.put(AppSystem.Backend.VC, "323");
		map.put(AppSystem.Backend.AML, "324");
		map.put(AppSystem.Backend.EPOLICY, "325");

		// 其他
		map.put(AppSystem.Others.TMANAGE, "400");
		map.put(AppSystem.Others.ITM, "401");
		map.put(AppSystem.Others.TMDATASVR, "402");
		map.put(AppSystem.Others.CEMAPP_ANDROID, "403");
		map.put(AppSystem.Others.CEMAPP_IOS, "404");
		map.put(AppSystem.Others.ISSUEPLAT, "405");
		map.put(AppSystem.Others.PICTUREPF, "406");
		map.put(AppSystem.Others.SMS, "407");

		final String json = JSON.toJSONString(map, SerializerFeature.MapSortField);
		System.out.println(json);
	}
}
