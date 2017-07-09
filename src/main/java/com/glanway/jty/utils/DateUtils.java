/**
 * Copyright (c) 2005, 2014  glanway.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * DateUtils 实现
 * update  2015-10-23:添加DateUtils类，并实现基本方法
 * @author zuo yang
 */
package com.glanway.jty.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


public class DateUtils {
	private static final Logger logger = Logger.getLogger(DateUtils.class);
	
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String DATETIME_FORMAT_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATETIME_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	/**
	 * 默认的格式化工具
	 */
	private static SimpleDateFormat formatTool = new SimpleDateFormat();
	
	/**
	 * 根据传入的时间获取对应的天数
	 * @param after
	 * @param before
	 * @return
	 */
	public static long getDays(Date after, Date before) {
		long l = after.getTime() - before.getTime();
		long nd = 1000 * 24 * 60 * 60;
		long day = l / nd;
		return day;
	}
	
	/**
	 * 检查str内容是否符合dateFormat格式的时间
	 * @param str
	 * @param dateFormat
	 * @return
	 */
	public static boolean isValidDate(String str, String dateFormat) {
		boolean convertSuccess = true;
		formatTool.applyPattern(dateFormat);
		try {
			formatTool.setLenient(false);
			formatTool.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
    /** 
     * 获取当前时间的前一天时间 
     * 返回时间格式 e.g. 2015-02-22
     * @param date 
     * @return 
     */ 
    public static Date getBeforeDay(Date date){  
    	 return getAfterDateByInt(date,-1);   
    }  
      
    /** 
     * 获取当前时间的后一天时间 
     * 返回时间格式 e.g. 2015-02-22
     * @param date 
     * @return 
     */  
    public static Date getAfterDay(Date date){  
        return getAfterDateByInt(date, 1);  
    }  
    
    /**
     * 获取 date的后 i天
     * 返回时间格式 e.g. 2015-02-22
     * @param date
     * @param i
     * @return
     */
    public static Date getAfterDateByInt(Date date, int i){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day + i);
		return dateFormat(calendar.getTime(), DATE_FORMAT_YYYY_MM_DD);
    }
    /**
     * 获取 本周第一天
    * 返回时间格式 e.g. 2015-02-22
     * @return
     */
    public static Date getCurrentWeekFirstDay(){
    	Calendar calendar = Calendar.getInstance(); 
    	calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY); 
        return dateFormat(calendar.getTime(),DATE_FORMAT_YYYY_MM_DD); 
    }
    
    /**
     * 获取 本月第一天
     * 返回时间格式 e.g. 2015-02-22
     * @return
     */
    public static Date getCurrentMonthFirstDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return dateFormat(calendar.getTime(), DATE_FORMAT_YYYY_MM_DD);
    }
    
	/**
	 * 获取上个月
	 * 
	 * @return
	 */
	public static Date getLastMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 取传入时间所在月第一天时间对象
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 日期减一,取得上月最后一天时间对象
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}
    
    /**
     * 获取 下个月第一天
     * 返回时间格式 e.g. 2015-02-22
     * @return
     */
    public static Date getAfterMonthFirstDay(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return dateFormat(calendar.getTime(), DATE_FORMAT_YYYY_MM_DD);
    }
    /** 
     * 当前季度的开始时间 
     * 返回时间格式 e.g. 2015-02-22 
     * @return 
     */ 
    public static Date getCurrentQuarterStartTime() { 
		Calendar calendar = Calendar.getInstance();
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				calendar.set(Calendar.MONTH, 0);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				calendar.set(Calendar.MONTH, 3);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				calendar.set(Calendar.MONTH, 4);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				calendar.set(Calendar.MONTH, 9);
			}
			calendar.set(Calendar.DATE, 1);
		} catch (Exception e) {
			logger.info("getCurrentQuarterStartTime Exception:" + e, e);
		}
		return dateFormat(calendar.getTime(), DATE_FORMAT_YYYY_MM_DD);
    } 
    /** 
     * 当前季度的结束时间 
     * 返回时间格式 e.g. 2015-02-22 
     * @return 
     */ 
    public  static Date getCurrentQuarterEndTime() { 
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				c.set(Calendar.MONTH, 2);
				c.set(Calendar.DATE, 31);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 5);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				c.set(Calendar.MONTH, 8);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DATE, 31);
			}
		} catch (Exception e) {
			logger.info("getCurrentQuarterEndTime Exception:" + e, e);
		}
		return dateFormat(c.getTime(), DATE_FORMAT_YYYY_MM_DD);
    } 
    /** 
     * 当前年的开始时间 
     * 返回时间格式 e.g. 2015-02-22 
     * @return 
     */ 
	public static Date getCurrentYearStartDate() {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DATE, 1);
		} catch (Exception e) {
			logger.info("getCurrentYearStartDate Exception:" + e, e);
		}
		return dateFormat(calendar.getTime(), DATE_FORMAT_YYYY_MM_DD);
	}

    /** 
     * 当前年的结束时间 
     * 返回时间格式 e.g. 2015-02-22
     * @return 
     */ 
	public static Date getAfterYearStartDate() {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.add(Calendar.MONTH, 12);
			calendar.set(Calendar.DATE, 1);
		} catch (Exception e) {
			logger.info("getCurrentYearStartDate Exception:" + e, e);
		}
		return dateFormat(calendar.getTime(), DATE_FORMAT_YYYY_MM_DD);
	}
    
    /**
     * 将传入的时间转换成format格式的字符串时间
     * @param date
     * @param format
     * @return
     */
	public static String date2Str(Date date, String format) {
		if (date == null) {
			return null;
		}
		formatTool.applyPattern(format);
		return formatTool.format(date);
	}
	
	/**
	 * 将date 类型时间转换成formatStr格式的字符串时间
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static Date dateFormat(Date date, String formatStr) {
		formatTool.applyPattern(formatStr);
		try {
			date = formatTool.parse(formatTool.format(date));
		} catch (ParseException e) {
			logger.info("Date parse exception" + e, e);
		}
		return date;
	}
	
	/**
	 * 将字符串时间转换成date 类型时间
	 * @param String
	 * @param Date
	 * @return
	 */
	public static Date str2Date(String dateStr, String formatStr) {
		if (StringUtils.isEmpty(dateStr)) {
			return null;
		}
		formatTool.applyPattern(formatStr);
		try {
			return formatTool.parse(dateStr);
		} catch (ParseException e) {
			logger.info("Date parse exception" + e, e);
		}
		return null;
	}
	
	/**
	 * 将Date格式化成符合默认日期格式的字符串
	 * 
	 * @param date
	 * @return 返回样例:2012-03-29
	 */
	public static String formatDate(Date date) {
		formatTool.applyPattern(DATE_FORMAT_YYYY_MM_DD);
		return formatTool.format(date);
	}
    /**
     * 获取当前时间之前或之后几分钟 minute
     *
     * @param minute
     * @return 返回样例:2012-03-29
     */
    public static String getTimeByMinute(int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minute);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

    }
	/**
	 * 将Date格式化成符合默认格式的字符串
	 * 
	 * @param date
	 * @return 返回样例:2012-03-29 14:32:23
	 */
	public static String format(Date date) {
		formatTool.applyPattern(DATETIME_FORMAT_YYYY_MM_DD_HHMMSS);
		return formatTool.format(date);
	}
	
	/**
	 * 判断是否是闰年
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeap(int year) {
		if (((year % 100 == 0) && year % 400 == 0)
				|| ((year % 100 != 0) && year % 4 == 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 返回当月天数
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMonthDays(int year, int month) {
		int days;
		int FebDay = 28;
		if (isLeap(year))
			FebDay = 29;
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			days = 31;
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			days = 30;
			break;
		case 2:
			days = FebDay;
			break;
		default:
			days = 0;
			break;
		}
		return days;
	}
	
	/**
	 * 根据传入日期获取还款日期
	 * 
	 * @param date
	 * @param monthTime 还款期数
	 * @param dayTime 还款日子
	 * @return
	 */
	public static Date getPayDate(Date date, int monthTime, int dayTime){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int payYear = c.get(Calendar.YEAR) + ((c.get(Calendar.MONTH) + monthTime - 11) / 12);
		int payMonth = (c.get(Calendar.MONTH) + monthTime) % 12;
		int thisMonthDays = getMonthDays(payYear, payMonth + 1);
		if (dayTime < 1) {
			dayTime = 1;
		}
		if (dayTime > thisMonthDays) {
			dayTime = thisMonthDays;
		}
		c.set(Calendar.YEAR, payYear);
		c.set(Calendar.MONTH, payMonth);
		c.set(Calendar.DATE, dayTime);
		return c.getTime();
	}
	
	/**
	 * 获得该月最后一天最后一秒
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthEndDate(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int thisMonthDays = getMonthDays(year, month + 1);
		c.set(Calendar.DATE, thisMonthDays);
		c.set(Calendar.HOUR, 11);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		return c.getTime();
	}
	
	public static Date format(Date date,String formatStr){
		SimpleDateFormat shortSdf = new SimpleDateFormat(formatStr); 
		try {
			date = shortSdf.parse(shortSdf.format(date));
		} catch (ParseException e) {
			logger.info("Date parse exception"+e,e);
		}
		return date;
	}
	
   public static Date strToDate(String datetime, String pattern){  
    	Date formatdate =null;
    	SimpleDateFormat format = new SimpleDateFormat(pattern); 
    	try {
    		formatdate = format.parse(datetime);
		} catch (ParseException e) {
			logger.info("Date parse exception"+e,e);
		}
        return formatdate;  
    }  
   
   /**
    * 返回开始结束时间， 1：今天2，本周3本月
 * @param datetype 1，2，3
 * @return
 */
public static Map<String, Object> getDateMapByType(String datetype){
	   Map<String,Object> map  = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(datetype)) {
			Calendar c = Calendar.getInstance();
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			if ("1".equals(datetype)) {
				//今日
				Date startDate = c.getTime() ;
				map.put("startDate", startDate);
				c.add(Calendar.DATE, 1);
				Date endDate = c.getTime() ;
				map.put("endDate", endDate);
			} else if ("2".equals(datetype)) {
		    	c.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY); 
				Date startDate = c.getTime();
				map.put("startDate", startDate);
				Date endDate = DateUtils.getAfterDateByInt(startDate, 7);
				map.put("endDate", endDate);
			} else if ("3".equals(datetype)) {
				//本月
				c.set(Calendar.DAY_OF_MONTH, 1);
				Date startDate = c.getTime() ;
				map.put("startDate", startDate);
				c.add(Calendar.MONTH, 1);
				c.set(Calendar.DAY_OF_MONTH, 1);
				Date endDate = c.getTime() ;
				map.put("endDate", endDate);
			}
		}
		return map;
   }

    /**
     * 计算两个日期之间相差的天数
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        smdate=sdf.parse(sdf.format(smdate));
        bdate=sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取当前时段名称
     * @return
     */
	public static String getCurrentTimeInterval() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour >= 6 && hour < 8) {
			return "早上好";
		} else if (hour >= 8 && hour < 11) {
			return "上午好";
		} else if (hour >= 11 && hour < 13) {
			return "中午好";
		} else if (hour >= 13 && hour < 18) {
			return "下午好";
		} else {
			return "晚上好";
		}
	}
	
	
}
