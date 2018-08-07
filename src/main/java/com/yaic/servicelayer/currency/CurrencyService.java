package com.yaic.servicelayer.currency;

/**
 *
 */
public interface CurrencyService
{

	/**
	 * @param sum 金额字符串
	 * @return string with RMB capital
	 */
	String convertToRMBCapital(String sum);

	/**
	 * @param money 金额字符串
	 * @return curreny format string 
	 */
	String formatMoney(String money);

	/**
	 * @param money 金额字符串
	 * @param symbol 是否显示货币符号
	 * @return curreny format string
	 *
	 *         <pre>
	 *         if symbol == ture return ￥ money else return money
	 *
	 *         <pre>
	 */
	String formatMoney(String money, boolean symbol);

	/**
	 * @param money 金额字符串
	 * @return curreny format string
	 */
	String formatMoney(Double money);

	/**
	 * @param money 金额字符串
	 * @param symbol 是否显示货币符号
	 * @return curreny format string
	 *
	 *         <pre>
	 *         if symbol == ture return ￥ money else return money
	 *
	 *         <pre>
	 */
	String formatMoney(Double money, boolean symbol);

	/**
	 * @param money 金额字符串
	 * @return curreny format string
	 */
	String formatMoney(Long money);

	/**
	 * @param money 金额字符串
	 * @param symbol 是否显示货币符号
	 * @return curreny format string
	 *
	 *         <pre>
	 *         if symbol == ture return ￥ money else return money
	 *
	 *         <pre>
	 */
	String formatMoney(Long money, boolean symbol);

	/**
	 * @param money 金额字符串
	 * @return curreny format string
	 */
	String formatMoney(Object money);

	/**
	 * @param money 金额字符串
	 * @param symbol 是否显示货币符号
	 * @return curreny format string
	 *
	 *         <pre>
	 *         if symbol == ture return ￥ money else return money
	 *
	 *         <pre>
	 */
	String formatMoney(Object money, boolean symbol);

	/**
	 * 判断是否为金额字符串且不超过2小数点
	 * @param money 金额字符串
	 * @return true if valid
	 */
	boolean isValid(String money);
}
