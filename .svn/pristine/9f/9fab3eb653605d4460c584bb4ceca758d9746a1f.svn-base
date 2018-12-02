package com.meme.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 * @author hzl
 *
 */
public class DateUtil {

	/**
	 * 获取系统当前时间
	 * @return
	 */
	public static Date getCurrentDate(){
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 获取系统当前日期时间格式化字符串
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format){
		String date = "";
		SimpleDateFormat sdf = null;
		if (format != null && !"".equals(format.trim())) {
			sdf = new SimpleDateFormat(format);
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		date = sdf.format(Calendar.getInstance().getTime());      
		return date;
	}
	/**
	 * 将类似 Mon Feb 13 08:00:00 GMT+08:00 2015格式的GMT时间格式化
	 * @param gmt
	 * @param format
	 * @return
	 */
	public static String transferGMT(String gmt,String format){
		SimpleDateFormat gmtFormat = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
		String dateString=null;
		try {
			Date date = gmtFormat.parse(gmt);
			SimpleDateFormat sdf = null;
			if (format != null && !"".equals(format.trim())) {
				sdf=new SimpleDateFormat(format);
			}else {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
			}
			dateString=sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateString;
	}
	
	/**
	 * 格式化日期输出字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date,String format){
		SimpleDateFormat sdf = null;
		if(StringUtil.isEmpty(format)){
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}else{
			sdf=new SimpleDateFormat(format);
		}
		if(date==null) return null;
		return sdf.format(date);
	}
	
	/**
	 * 只输出年月日
	 * @param date
	 * @return
	 */
	public static String onlyDate(Date date){
		SimpleDateFormat myFormat=new SimpleDateFormat("yyyy-MM-dd");
		return myFormat.format(date);
	}
	
	/**
	 * 比较两个日期的大小，date1>date2返回1；date1=date2返回0；date1<date2返回-1
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static Integer compare(String date1,String date2){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1;
		try {
			d1 = sdf.parse(date1);
			Date d2=sdf.parse(date2);
			if(d1.getTime()>d2.getTime()) return 1;
			else if(d1.getTime()<d2.getTime()) return -1;
			else return 0;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 毫秒转成天时分秒表示
	 * @param milSec
	 * @return
	 */
	public static String millisec2CNDate(Long milSec){
		int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = milSec / dd;
        long hour = (milSec - day * dd) / hh;
        long minute = (milSec - day * dd - hour * hh) / mi;
        long second = (milSec - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = milSec - day * dd - hour * hh - minute * mi - second * ss;
        
        String strDay = day < 10 ? "0" + day : "" + day;//天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        
        return strDay+" 天 "+strHour+" 小时 "+strMinute + " 分钟 " + strSecond + " 秒 "+strMilliSecond+" 毫秒";
	}
}
