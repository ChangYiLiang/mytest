package com.chyl.mytest.util;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串的一些常用方法
 * 
 * @version 1.0
 * @author
 */
public class StringUtil {
	/**
	 * 生成uuid
	 * @return
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	/**
	 * 生成uuid
	 * @return
	 */
	public static String get15UUID(){
		return UUID.randomUUID().toString().replaceAll("-","").substring(0,15);
	}

	/**
	 * 判断字符串是否全为数字
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumber(String str) {
		String str2 = str.trim();
		for (int i = 0; i < str2.length(); i++) {
			if (!Character.isDigit(str2.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断字符串是否只包含A-Za-z0-9
	 * @return boolean
	 */
	public static boolean checkPwd(String pwd) {
		if(pwd.matches("[0-9A-Za-z]*") && StringUtil.checkLengthBetween(pwd, 6, 16)){
			return true;
		}
		return false;
	}

	/**
	 * 处理手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static String handlePhone(String phone) {
		if(StringUtil.isEmpty(phone)){
			phone = "";
		}
		return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
	}

	/**
	 * 处理身份证号码
	 * 
	 * @param cardId
	 * @return
	 */
	public static String handleCardId(String cardId) {
		if(StringUtil.isEmpty(cardId)){
			cardId = "";
		}
		return cardId.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1******$2");
	}
	
	/**
	 * 处理银行卡号
	 * 
	 * @param
	 * @return
	 */
	public static String handleBankNo(String bankAccount) {
		if (StringUtil.isEmpty(bankAccount)) { return "";}
		int len = bankAccount.length();
		String account = "**** **** **** ";
		if(len<4){
			account = "";
		} else {
			account = account + bankAccount.substring((len-4), len);
		}
		return account;
	}

	/**
	 * 判断字符串是否为11位数字，手机号码验证之一
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean is11Number(String str) {
		if (isNumber(str)) {
			if (str.trim().length() == 11) {
				return true;
			}
			return false;
		}
		return false;

	}

	/**
	 * 格式化数字 5555.33 -> 5,555.33
	 * 
	 * @param
	 * 
	 * @return 格式化后的数字
	 */
	public static String formatNum(double num) {
		DecimalFormat df = new DecimalFormat(",###.00");
		return df.format(num);

	}

	/**
	 * 判断字符串否空
	 * 
	 * @param str
	 *            --字符串
	 * @return boolean
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * 验证字符串是否是指定长度
	 * 
	 * @param str
	 *            -- 字符串
	 * @param length
	 *            -- 指定长度
	 * @return boolean
	 */
	public static boolean checkLength(String str, int length) {
		if (!str.isEmpty() && str.trim().length() == length)
			return true;
		return false;
	}

	/**
	 * 验证字符串长度是否在指定长度之间，闭区间 [ ]
	 * 
	 * @param str
	 *            -- 字符串
	 * @param length1
	 *            -- 指定长度1
	 * @param length2
	 *            -- 指定长度2
	 * @return boolean
	 */
	public static boolean checkLengthBetween(String str, int length1, int length2) {
		if (!str.isEmpty() && str.trim().length() >= length1 && str.trim().length() <= length2)
			return true;
		return false;
	}

	/**
	 * 判断字符串中间是否含有空格
	 * 
	 * @param str
	 *            -- 字符串
	 * @return boolean
	 */

	public static boolean hasSpace(String str) {
		int index = str.trim().indexOf(" ");
		if (index > 0)
			return true;
		return false;
	}

	/**
	 * 获取一个随机数
	 * 
	 * @param --指定返回的随机数位数
	 * @return --字符串格式的随机数
	 */
	public static String getRandom(int i) {
		Random random = new Random();
		if (i == 0)
			return "";
		String str = "";
		for (int k = 0; k < i; k++) {
			str = str + random.nextInt(9);
		}
		return str;
	}

	/**
	 * 获取字符串的长度，一个汉字占两个字符
	 * 
	 * @param str
	 *            --字符串内容
	 * @return --字符串长度
	 */
	public static int getCharLength(String str) {
		int realLength = 0;
		int len = str.length();
		int charCode = -1;
		for (int i = 0; i < len; i++) {
			charCode = str.charAt(i);
			if (charCode >= 0 && charCode <= 128) {
				realLength += 1;
			} else {
				// 如果是中文则长度加2
				realLength += 2;
			}
		}
		return realLength;
	}

	/**
	 * 简化加签、验签时字符串拼接
	 * 
	 * @param strings
	 *            <br>
	 *            -- 多个字符串参数
	 * @return -- 拼接后的字符串
	 */
	public static String forEncryptStr(String... strings) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < strings.length; i++) {
			buffer.append(StringUtils.trimToEmpty(strings[i]));
		}
		return buffer.toString();
	}

	/**
	 * 验证手机号码格式
	 */
	public static boolean checkMobile(String mobile) {
		//String reg = "^1[34578]{1}\\d{9}$";
		String reg = "^1[0-9]{10}$";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

    /** 
     * 根据身份编号获取性别 
     *  
     * @param cardId 
     *            身份编号 
     * @return 性别(M-男，F-女) 
     */  
    public static String getGenderByIdCard(String cardId) {  
        String sGender = "";  
        String sCardNum = cardId.substring(16, 17);  
        if (Integer.parseInt(sCardNum) % 2 != 0) {  
            sGender = "M";  
        } else {  
            sGender = "F";  
        }  
        return sGender;  
    }
    
    /** 
     * 根据身份编号获取性别 
     *  
     * @param cardId 
     *            身份编号 
     * @return 性别(1-男，2-女) 
     */  
    public static int getGenderByIdCard2(String cardId) {  
        int sGender = 0;  
        String sCardNum = cardId.substring(16, 17);  
        if (Integer.parseInt(sCardNum) % 2 != 0) {  
            sGender = 1;  
        } else {  
            sGender = 2;  
        }  
        return sGender;  
    } 
    
    /** 
     * 不包含或只包含一个小数点，金额验证
     */  
    public static boolean point(String num) {  
    	if (isEmpty(num)) return false;
    	if ("0".equals(num)) {
    		return true;
    	}
    	return num.matches("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");  
    } 
    
    /** 
     * 计算地球上任意两点(经纬度)距离 
     *  
     * @param long1 
     *            第一点经度 
     * @param lat1 
     *            第一点纬度 
     * @param long2 
     *            第二点经度 
     * @param lat2 
     *            第二点纬度 
     * @return 返回距离 单位：米 
     */  
    public static int distance(double long1, double lat1, double long2,  
            double lat2) {  
        double a, b, R;  
        R = 6378137; // 地球半径  
        lat1 = lat1 * Math.PI / 180.0;  
        lat2 = lat2 * Math.PI / 180.0;  
        a = lat1 - lat2;  
        b = (long1 - long2) * Math.PI / 180.0;  
        double d;  
        double sa2, sb2;  
        sa2 = Math.sin(a / 2.0);  
        sb2 = Math.sin(b / 2.0);  
        d = 2  
                * R  
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)  
                        * Math.cos(lat2) * sb2 * sb2));
        int distance = new Double(d).intValue();
        return distance;  
    }
    
	/**
	 * 对小于10的数字前面补0
	 * @param numStr
	 * @return
	 */
	public static String suppleZore(String numStr) {
		int num = 0;
		if(StringUtil.isEmpty(numStr)) return "00";
		try {
			num = Integer.valueOf(numStr);
			return num < 10 ? ("0"+num) : (String.valueOf(num));
		} catch (Exception e) {
			System.out.println("parse error, numStr = " + numStr);
		}
		return "00";
	}

	public static String generateCode() {
		return generateCode(6);
	}

	public static String generateCode(int b) {
		StringBuilder code = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < b; i++) {
			code.append(random.nextInt(9));
		}
		return code.toString();
	}

	/***
	 * 生成流水号
	 * @return
	 */
	public static String geneTradeNo() {
		StringBuffer buffer = new StringBuffer(DateUtil.getCurrentDate("YYYYMMddHHmmssSSSS").toString());
		Random random = new Random();
		int num = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
		buffer = buffer.append(num);
		return buffer.toString();
	}

	public static String getRandomNumber(int number) {
		int num[] = new int[number];
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<num.length;i++) {
			num[i] = (int)(10*(Math.random()));
			buffer.append(num[i]);
		}
		return buffer.toString();
	}
}
