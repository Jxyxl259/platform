package com.yaic.servicelayer.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Qu Dihuai
 *
 */
public enum IdProvinceEnum
{
	// 11-15 京 津 冀 晋 蒙
	/**
	 * 北京
	 */
	BEIJING(11, "北京"),
	/**
	 * 天津
	 */
	TIANJIN(12, "天津"),
	/**
	 * 河北
	 */
	HEBEI(13, "河北"),
	/**
	 * 山西(陕西简称晋)
	 */
	JIN(14, "山西"),
	/**
	 * 内蒙古
	 */
	NEIMENGGU(15, "内蒙古"),

	// 21-23 辽 吉 黑
	/**
	 * 辽宁
	 */
	LIAONING(21, "辽宁"),
	/**
	 * 吉林
	 */
	JILIN(22, "吉林"),
	/**
	 * 黑龙江
	 */
	HEILONGJIANG(23, "黑龙江"),

	// 31-37 沪 苏 浙 皖 闽 赣 鲁
	/**
	 * 上海
	 */
	SHANGHAI(31, "上海"),
	/**
	 * 江苏
	 */
	JIANGSU(32, "江苏"),
	/**
	 * 浙江
	 */
	ZHEJIANG(33, "浙江"),
	/**
	*
	*/
	ANHUI(34, "安徽"),
	/**
	 * 福建
	 */
	FUJIAN(35, "福建"),
	/**
	 * 江西
	 */
	JIANGXI(36, "江西"),
	/**
	 * 山东
	 */
	SHANDONG(37, "山东"),

	// 41-46 豫 鄂 湘 粤 桂 琼
	/**
	 * 河南
	 */
	HENAN(41, "河南"),
	/**
	 * 湖北
	 */
	HUBEI(42, "湖北"),
	/**
	 * 湖南
	 */
	HUNAN(43, "湖南"),
	/**
	 * 广东
	 */
	GUANGDONG(44, "广东"),
	/**
	 * 广西
	 */
	GUANGXI(45, "广西"),
	/**
	 * 海南
	 */
	HAINAN(46, "海南"),
	/**
	 *
	 */
	CHONGQING(50, "重庆"),

	// 50-54 渝 川 贵 云 藏
	/**
	 * 四川
	 */
	SICHUAN(51, "四川"),
	/**
	 * 贵州
	 */
	GUIZHOU(52, "贵州"),
	/**
	 * 云南
	 */
	YUNNAN(53, "云南"),
	/**
	 * 西藏
	 */
	XIZANG(54, "西藏"),

	// 61-65 陕 甘 青 宁 新
	/**
	 * 陕西(陕西简称陕)
	 */
	SHAN(61, "陕西"),
	/**
	 * 甘肃
	 */
	GANSU(62, "甘肃"),
	/**
	 * 青海
	 */
	QINGHAI(63, "青海"),
	/**
	 * 青海
	 */
	NINGXIA(64, "青海"),
	/**
	 * 新疆
	 */
	XINJIANG(65, "新疆"),

	// 81-82 港 澳
	/**
	 * 香港
	 */
	XIANGGANG(81, "香港"),
	/**
	 * 澳门
	 */
	AOMEN(82, "澳门");

	/**
	 * 省\直辖市代码
	 */
	private int code;
	/**
	 * 省\直辖市名称
	 */
	private String title;

	private static Map<Integer, IdProvinceEnum> cache;
	static
	{
		final Map<Integer, IdProvinceEnum> map = new HashMap<>(33);
		for (final IdProvinceEnum idProvinceEnum : IdProvinceEnum.values())
		{
			map.put(idProvinceEnum.code(), idProvinceEnum);
		}
		cache = Collections.unmodifiableMap(map);
	}

	private IdProvinceEnum(final int code, final String title)
	{
		this.code = code;
		this.title = title;
	}

	/**
	 * 省\直辖市代码
	 *
	 * @return code
	 */
	public int code()
	{
		return code;
	}

	/**
	 * 省\直辖市名称
	 *
	 * @return title
	 */
	public String title()
	{
		return title;
	}

	/**
	 * @param code
	 * @return Province
	 */
	public static IdProvinceEnum fromCode(final int code)
	{
		return cache.get(code);
	}
}
