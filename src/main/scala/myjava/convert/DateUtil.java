package myjava.convert;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @desc   时间日期工具类
 * @author brilliance.Ke
 * @version 1.0    2015.04.11
 */
public class DateUtil {
	
	static SimpleDateFormat fmt = new SimpleDateFormat();
	
	/**
	 * 字符串日期转换成java.util.Date对象
	 * @param strDate 日期字符串
	 * @return java.util.Date对象
	 */
	public static Date string2Date(String strDate) {
		Date date = null;
		try {
			if (strDate != null && !strDate.equals("")) {
				if (strDate.indexOf("-") != -1) {
					fmt.applyPattern("yyyy-MM-dd");
				} else if (strDate.indexOf("/") != -1) {
					fmt.applyPattern("yyyy/MM/dd");
				} else if (strDate.length() == 8) {
					fmt.applyPattern("yyyyMMdd");
				}
				date = fmt.parse(strDate);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 字符串日期转换成java.util.Date对象
	 * 格式 ：yyyy-MM-dd HH:mm:ss;yyyyMMddHHmmss
	 * @param strDate 日期字符串
	 * @return java.util.Date对象
	 */
	public static Date string2DateTime(String strDate) {
		Date date = null;
		try {
			if (strDate != null && !strDate.equals("")) {
				if (strDate.indexOf("-") != -1) {
					fmt.applyPattern("yyyy-MM-dd HH:mm:ss");
				} else if (strDate.indexOf("/") != -1) {
					fmt.applyPattern("yyyy/MM/dd HH:mm:ss");
				} else if (strDate.length() == 8) {
					fmt.applyPattern("yyyyMMddHHmmss");
				}
				date = fmt.parse(strDate);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	
	/**
	 * java.util.Date对象转换成字符串日期
	 * @param strDate 日期字符串(yyyyMMdd)
	 * @return 字符串日期
	 */
	public static String date2String(Date date) {
		fmt.applyPattern("yyyyMMdd");
		String strDate = fmt.format(date);
		return strDate;
	}
	
	/**
	 * java.util.Date对象转换成字符串日期
	 * @param strDate 日期字符串
	 * @param pattern  转换模式字符串
	 * @return 字符串日期
	 */
	public static String date2String(Date date,String pattern) {
		fmt.applyPattern(pattern);
		String strDate = fmt.format(date);
		return strDate;
	}
	
	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int subDay(Date date1,Date date2) {
		
		long timeNow = date1.getTime();
		long timeOld = date2.getTime();
		long lngSub = (timeNow - timeOld) / (1000 * 60 * 60 * 24);
		if(lngSub<0){
			return 0;
		}else {
			return (int)lngSub;
		}
	}
	
	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return	
	 */
	public static int subMonth(Date date1,Date date2) {
		
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		
		int y = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		int m = c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		
		int d = c1.get(Calendar.DAY_OF_MONTH) - c2.get(Calendar.DAY_OF_MONTH);
		d = d >= 0 ? 0 : 1;
		int n = y*12 + m + d;
		
		return n;
	}
	
	
	/**
	 * 获取当前日期
	 * @return 当前日期
	 */
	public static Date getCurrentDate() {
		Calendar calDate = Calendar.getInstance();
		return calDate.getTime();
	}
	
	/**
	 * 获取当前字符串日期
	 * @param pattern 转换模式字符串
	 * @return
	 */
	public static String getCurrentDate(String pattern) {
		
		String strCurDate = DateUtil.date2String(getCurrentDate(),pattern);
		return strCurDate;
	}
	
	
	
	/**
	 *  日期月份增加
	 * @param date 日期
	 * @param amount 添加月份数
	 * @return 返回月份增加后日期
	 */
	public static Date addMonth(Date date, int amount) {
		//
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		//月加
		cld.add(cld.MONTH, amount);
		
		return cld.getTime();
	}
	
	
	/**
	 * 获取date的天数
	 *
	 * @param date java.util.Date对象
	 * @return 月份中的天数
	 */
	public static int getDateDay(Date date) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
		
	}
	
	/**
	 * 获取date的月份
	 *
	 * @param date java.util.Date对象
	 * @return 月份
	 */
	public static int getDateMonth(Date date) {
		
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH);
	}
	/**
	 * 获取前一天时间
	 * @return
	 */
	public static Date getYesDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date date = calendar.getTime();
		return date;
	}
	
	
	
}