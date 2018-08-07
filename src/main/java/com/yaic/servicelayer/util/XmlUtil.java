package com.yaic.servicelayer.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * XML字符串和具体类相互转化
 */
public class XmlUtil {
	/**
	 * java 转换成xml
	 *
	 * @param object
	 * @return String xml字符串
	 */
	public static String toXml(final Object object)
	{
		if(ObjectUtil.isNull(object)) {
			return null;
		}
		final XStream xstream = new XStream();
		xstream.processAnnotations(object.getClass());
		return xstream.toXML(object);
	}
	/**
	 * 将传入xml文本转换成Java对象
	 *
	 * @Title: toBean
	 * @param xmlString
	 * @param clazz
	 *           xml对应的class类
	 * @return T xml对应的class类的实例对象
	 *
	 *         调用的方法实例：PersonBean person=XmlUtil.toBean(xmlStr, PersonBean.class);
	 */
	public static <T> T toBean(final String xmlString, final Class<T> clazz)
	{
		if(StringUtil.isEmpty(xmlString) || ObjectUtil.isNull(clazz)) {
			return null;
		}
		final XStream xstream = new XStream(new DomDriver());
		xstream.ignoreUnknownElements(); //忽视xmlStr多余元素
		xstream.processAnnotations(clazz);

		@SuppressWarnings("unchecked")
		final T obj = (T) xstream.fromXML(xmlString);
		return obj;
	}

	/**
	 * 将传入xml文本转换成Java对象
	 * @param xmlStr
	 * @param clazz
	 * 		xml对应的class类
	 * @return Object
	 */
	public static <T> T toBeanIgnore(final String xmlStr, final Class<T> clazz)
	{
		if(StringUtil.isEmpty(xmlStr) || ObjectUtil.isNull(clazz)) {
			return null;
		}
		final XStream xstream = new XStream()
		{
			@Override
			protected MapperWrapper wrapMapper(final MapperWrapper next)
			{
				return new MapperWrapper(next)
				{
					@Override
					public boolean shouldSerializeMember(@SuppressWarnings("rawtypes") final Class definedIn, final String fieldName)
					{
						if (definedIn == Object.class)
						{
							return false;
						}
						return super.shouldSerializeMember(definedIn, fieldName);
					}
				};
			}
		};
		xstream.processAnnotations(clazz);

		@SuppressWarnings("unchecked")
		final T obj = (T) xstream.fromXML(xmlStr);
		return obj;
	}
	
}
