/**
 * (C) Copyright esite Corporation 2011
 *       All Rights Reserved.
 * 2011-1-14
 * zhangzf
 * DateHelper.java
 * esite-web-framework
 */
package com.esite.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *<PRE>
 * 功能描述:开发者在此描述类的主要功能或目的
 * 修改历史:
 * -----------------------------------------------------------------------------
 * 		VERSION		DATE		BY			CHANGE/COMMENT
 * -----------------------------------------------------------------------------
 * 		1.0			2011-1-14	zhangzf		create
 * -----------------------------------------------------------------------------
 * </PRE>
 */
public class DateHelper {
	public static SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat MM_DD = new SimpleDateFormat("MM-dd");
	public static SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat CHINESE_YYYY_MM_DD = new SimpleDateFormat("yyyy年MM月dd日");
	public static SimpleDateFormat CHINESE_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
	public static SimpleDateFormat CHINESE_YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
	public static SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String foramt(Date date, String dateFormat){
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(date);
	}
}
