/**
 * File: convert.java
 * General 
 * @author luosong
 * version 1.0 2015年12月16日: 下午4:23:11
 * Copyright (C) 2008-2015 oneapm.com all rights reserved
 */
package myjava.convert;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {
	
	
	/**
	 * 字符串转日期
	 */
	public static Date stringConDate(String strDate) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Date date = sdf.parse(strDate);
		return date;
	}
	
	/**
	 * 获取当前时间与1970的毫秒值，常用于比较
	 * @param args
	 * @throws Exception 
	 * @throws Exception
	 */
	public static long msec1970() throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.parse(sdf.format(new Date())).getTime();
	}
	
	/**
	   * 获取现在时间
	   * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	   */
	public static Date strToDateLong(String strDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   ParsePosition pos = new ParsePosition(0);
	   Date strtodate = formatter.parse(strDate, pos);
	   return strtodate;
	}
	
	public static void main(String[] args) throws Exception {
		Date date1 = stringConDate("16:38:20");
		Date date2 = stringConDate("16:38:25");
		System.out.println("date1==="+date1);
		System.out.println("date2==="+date2);
		if(date1.before(date2)){
//			System.out.println(date1 - date2);
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"); // 格式化
		System.out.println("3==="+sdf.format(new Date()));
		System.out.println("4==="+sdf.parse(sdf.format(new Date())));
		System.out.println("5==="+sdf.parse(sdf.format(new Date())).getTime());
		System.out.println("6==="+msec1970());
		
	}
}
