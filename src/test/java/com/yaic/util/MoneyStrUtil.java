package com.yaic.util;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yaic.servicelayer.util.StringUtil;

/**
 * 转换货币值
 *
 */
public class MoneyStrUtil {
	
    public static boolean isNumber(String str){   
        Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式  
        Matcher match=pattern.matcher(str);   
        if(match.matches()==false){   
           return false;   
        }else{   
           return true;   
        }   
    } 
	
	/**
	 * 传入数字字符串转换成货币值
	 * @param money
	 * @return
	 */
	public static String formatMoney(String money){
		return formatMoney(money, null);
	}
	
	
	/**
	 * 传入数字字符串转换成货币值
	 * b==true 显示￥
	 * @param money
	 * @return
	 */
	public static String formatMoney(String money,Boolean b){
		
		if(StringUtil.isNotEmpty(money)){
			Double m=Double.valueOf(money);
			return formatMoney(m,b);
		}
		
		return "0.00";
	}
	
	/**
	 * 传入Double转换成货币值
	 * @param money
	 * @return
	 */
	public static String formatMoney(Double money){
		return formatMoney(money, null);
	}
	
	/**
	 * 传入Double转换成货币值
	 * b==true 显示￥
	 * @param money
	 * @return
	 */
	public static String formatMoney(Double money,Boolean b){
		
		if(b==null){
			b=false;
		}
		
		if(money != null){
			int index=1;
			String v="";
			if(money<0){
				index=2;
				v="-";
			}
			NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
			
			String m=currencyFormat.format(money);
			if(!b){
				m=m.substring(index, m.length());
			}
		       return v+m;
		}
		
		return "0.00";
	}
	
	/**
	 * 传入Long转换成货币值
	 * @param money
	 * @return
	 */
	public static String formatMoney(Long money){
		return formatMoney(money, null);
	}
	
	/**
	 * 传入Long转换成货币值
	 * b==true 显示￥
	 * @param money
	 * @return
	 */
	public static String formatMoney(Long money,Boolean b){
		
		if(b==null){
			b=false;
		}
		
		if(money != null){
			int index=1;
			String v="";
			if(money<0){
				index=2;
				v="-";
			}
			NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
			String m=currencyFormat.format(money);
			if(!b){
				m=m.substring(index, m.length());
			}
		       return v+m;
		}
		
		return "0.00";
	}
	
	
	/**
	 * 传入Long转换成货币值
	 * @param money
	 * @return
	 */
	public static String formatMoney(Object money){
		return formatMoney(money, null);
	}
	
	/**
	 * 传入Long转换成货币值
	 * b==true 显示￥
	 * @param money
	 * @return
	 */
	public static String formatMoney(Object money,Boolean b){
		
		if(money != null){
			Double m=Double.valueOf(String.valueOf(money));
			return formatMoney(m,b);
		}
		
		return "0.00";
	}
}
