package com.pps.usmovie.mobile.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class DateUtil {
	
	public static final int DATEPART_YEAR			=	0;
	public static final int DATEPART_MONTH			=	1;
	public static final int DATEPART_DAY			= 	2;
	public static final int DATEPART_HOUR			= 	4;
	public static final int DATEPART_MINUTE			= 	8;
	public static final int DATEPART_SECOND			= 	16;
	public static final int DATEPART_MILLISECOND	= 	32;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat sdf_pic_data = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	private static final SimpleDateFormat sdf_usa = new SimpleDateFormat("EEEE, dd MMM yyyy",Locale.US);
	
	private static Date currentDate = new Date();
	public static String date_str = formatDate(currentDate);
	public static String date_str_year = date_str.substring(0,4);
	
	
	/**获取当前时间
	 * @return
	 */
	public static String getCurrentTime(){
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		Date date = new Date(System.currentTimeMillis());
		
		return format.format(date);
	}
	
	public static String getCurDateStr() {
		return date_str;
	}
	 
	public static String getCurDateTimeStr() {
		return sdf.format(new Date());
	}
	
	public static String currentDateTimeStr2() {
		return sdf_pic_data.format(new Date());
	}
	
	
	public static String getCurrentDateStr() {
		Calendar now = Calendar.getInstance(); 
		return String.valueOf(now.get(Calendar.YEAR))+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DATE);
	}
	
	public static String getCurrentDateAllStr(){
		Calendar now = Calendar.getInstance(); 
		return String.valueOf(now.get(Calendar.YEAR)) + "-"
				+ now.get(Calendar.MONTH) + "-" + now.get(Calendar.DATE) + " "
				+ now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE) + ":"
				+ now.get(Calendar.SECOND);
	}

	/**
	 * 获得当前时间的字符串 year-month-day
	 * @param d
	 * @return
	 */
	public static String getDateStrByDate(Date d){
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		String time = String.valueOf(now.get(Calendar.YEAR))+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DATE);
		return time;
	}
	/**
	 * 将日期改成字符串
	 * @param d
	 * @return
	 */
	public static String getDetailDateStrByDate(Date d){
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		calendar.setTime(d);
		return sdf.format(calendar.getTime());
	}
	/**
	 * 获得时分�?
	 * @return
	 */
	public static String getCurrentTime1() {
		char splitChar = ':';
		Calendar calendar = Calendar.getInstance();
		StringBuffer sb = new StringBuffer();
		sb.append(calendar.get(Calendar.HOUR_OF_DAY)).append(splitChar);
		sb.append(calendar.get(Calendar.MINUTE)).append(splitChar);
		sb.append(calendar.get(Calendar.SECOND));
		return sb.toString();
	}
	/**
	 * 获得两个日期的时间差 按秒来算
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDiffSenconds(Date d1,Date d2){
		long l = (d2.getTime()-d1.getTime());
		int senconds = (int)Math.ceil(l/1000.0);
		return senconds;
	}
	/**
	 *  
	 */
	public static long getDiff(Date d1,Date d2,int datepart){
		if(d1 == null || d2==null) return -1;
		long result = 0;
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		c2.get(Calendar.YEAR);
		switch(datepart){
		case DATEPART_MILLISECOND:
			result = d1.getTime()-d2.getTime();
			break;
		case DATEPART_SECOND:
			result = (d1.getTime()-d2.getTime())/1000;
			break;
		case DATEPART_MINUTE:
			result = (d1.getTime()-d2.getTime())/(1000*60);
			break;
		case DATEPART_HOUR:
			result = (d1.getTime()-d2.getTime())/(1000*60*60);
			break;
		case DATEPART_DAY:
			result = (d1.getTime()-d2.getTime())/(1000*60*60*24);
			break;
		case DATEPART_MONTH:
			result = ( c1.get(Calendar.YEAR)-c2.get(Calendar.YEAR))*12+c1.get(Calendar.MONTH)-c2.get(Calendar.MONTH);
			break;
		case DATEPART_YEAR:
			result = c1.get(Calendar.YEAR)-c2.get(Calendar.YEAR);
			break;
		}
		return result;
	}
	/**
	 * 获得当前的年
	 * @return
	 */
	public static int getYearInt() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.YEAR);
	}
	/**
	 * 获得当前的月�?
	 * @return
	 */
	public static int getMonthInt() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.MONTH);
	}
	/**
	 * 获得当前的日�?
	 * @return
	 */
	public static int getDateInt() {
		Calendar now = Calendar.getInstance();
		return now.get(Calendar.DATE);
	}
	/**
	 * 获得当前的时�?
	 * @return
	 */
	public static long getInt() {
		Calendar now = Calendar.getInstance();
		return now.getTime().getTime();
	}
	
	public static int getFieldInt(int field) {
		Calendar now = Calendar.getInstance();
		return now.get(field);
	}
	
	/**
	 * (year-month-date) - now
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static int getDiffSenconds(int year, int month, int date) {
		Calendar now = Calendar.getInstance();
		Calendar remove = Calendar.getInstance();
		remove.set(Calendar.YEAR, year);
		remove.set(Calendar.MONTH, month - 1);
		remove.set(Calendar.DATE, date);
		return getDiffSenconds(now.getTime(), remove.getTime());
	}
	
	public static int getDiffSenconds(long time) {
		Calendar now = Calendar.getInstance();
		long l = time - now.getTime().getTime();
		int senconds = (int)Math.ceil(l/1000.0);
		return senconds;
	}
	
	 
	public static String[] getWeekTime(){
		Calendar now = Calendar.getInstance();
		Date firstDay = new Date();
		firstDay.setTime(now.getTime().getTime()-(now.get(Calendar.DAY_OF_WEEK)-1)*24*3600*1000);
		String[] result = new String[7];
		now.setTime(firstDay);
		for(int i=0;i<7;i++){
			String time = String.valueOf(now.get(Calendar.YEAR))+"-"+(now.get(Calendar.MONTH)+1)+"-"+now.get(Calendar.DATE);
			result[i]= time;
			now.setTime(new Date(now.getTime().getTime()+24*3600*1000));
		}
		return result;
	}

	/**
	 * 23:00 - 4:00, 杩斿洖true 
	 * @return
	 */
	public static boolean isAtNight() {
		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		
		if (hour >= 23 || hour < 4) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Date parseIntsToDate(int year, int month, int day) {
		try {
			Calendar ca = Calendar.getInstance();
			ca.set(Calendar.YEAR, year);
			ca.set(Calendar.MONTH, month - 1);
			ca.set(Calendar.DATE, day);
			return ca.getTime();
		} catch (RuntimeException e) {
			return null;
		}
	}
	
	//String转成date类型
    public static Date parse(String in)
    {
    	Date ret = null;
    	try {
    		if(in!=null && !"".equals(in))
			ret = sdf.parse(in);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
    }
    
    //Date转成String
    public static String format(Date in)
    {
    	return in==null?null:sdf.format(in);
    }
    
    public static String formatDate(Date in)
    {
    	return in==null?null:sdf_date.format(in);
    }
    
    public static Date parseDate(String in)
    {
    	Date ret = null;
    	try {
    		if(in!=null && !"".equals(in))
			ret = sdf_date.parse(in);
		} catch (ParseException e) {
		}
		return ret;
    }
    
    public static Date addDate(Date in,int days)
    {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(in);
    	cal.add(Calendar.DAY_OF_YEAR, days);
    	return cal.getTime();
    	
    }
    
    public static Date getLocaleDate(Date prc)
    {
    	return prc==null?null:new Date(prc.getTime()+getDiffTimeZone());
    }
    
    public static String getLocaleDate(String prc)
    {
    	return prc==null?null:format(getLocaleDate(parse(prc)));
    }
    
    public static Date getServerDate(Date locale)
    {
    	return locale==null?null:new Date(locale.getTime()-getDiffTimeZone());
    }
    
    public static String getServerDate(String locale)
    {
    	return locale==null?null:format(getServerDate(parse(locale)));
    }
    
    private static long getDiffTimeZone()
    {
        return TimeZone.getDefault().getRawOffset()   
        - TimeZone.getTimeZone("PRC").getRawOffset();   
    }
    
    public static boolean isToday(String date)
    {
    	return date.startsWith(date_str);
    }
    
    public static boolean isToday(Date date)
    {
    	return isToday(sdf_date.format(date));
    }
    
    public static String getDisplayDate(String date)
    {
    	 if(isToday(date))
    		 return date.substring(11,16);
    	 else if(date.startsWith(date_str_year))
    		 return date.substring(5,10);
    	 else
    		 return date.substring(0,10);
    }
    
    public static Date getStrartDate()
    {
    	return currentDate;
    }

    public static String getUSDate()
    {
    	return sdf_usa.format(currentDate);
    }
    /**
     * 根据格式，将时间戳改成格式匹配的格式
     * @param birthday
     * @param string
     * @return
     */
	public static String getFormatDateTime(String timeStr, String format) {
		//不足14位，应补上三个0
		if(TextUtils.isEmpty(timeStr)){
			return "";
		} else	if(timeStr.length() < 14) {
			timeStr +="000";
		}
		if(TextUtils.isEmpty(format)){
			format = "yyyy-MM-dd";
		}
		SimpleDateFormat df=new SimpleDateFormat(format);
		Date date = new Date(Long.parseLong(timeStr));
		String time=df.format(date);
		return time;
	}
	/**获得时间戳  by Calendar*/
    public static String getTimeStamps(Calendar calender){
    	return  String.valueOf(calender.getTimeInMillis()).substring(0, 10);//.substring(0, 10)
    }
    
    /**获得时间戳  by Date*/
    public static String getTimeStamps(Date date){
    	return  String.valueOf(date.getTime()).substring(0, 10);//.substring(0, 10)
    }
    
    /**获得时间戳  by Date*/
    public static String getTimeStamps(String date){
    	return  String.valueOf(parse(date).getTime()).substring(0, 10);//.substring(0, 10)
    }
    
    /**获得时间戳  by Calendar*/
    public static long getLongTimeStamps(Calendar calender){
    	return  calender.getTimeInMillis() / 1000;//.substring(0, 10)
    }
    
    /**获得时间戳  by Date*/
    public static long getLongTimeStamps(Date date){
    	return  date.getTime() / 1000;//.substring(0, 10)
    }
    
    /**获得时间戳  by Date*/
    public static long getLongTimeStamps(String date){
    	return  parse(date).getTime() / 1000;//.substring(0, 10)
    }
    
    /**时间戳 转String by long*/
    public static String getTime(long timestamp){
  		return sdf.format(new Date(timestamp * 1000L));
     }
    
	public static String showTime(Date ctime, String format) {
		String r = "";
		if(ctime==null)return r;
		if(format==null)format="yyyy-MM-dd HH:mm";

		long nowtimelong = System.currentTimeMillis();
		long ctimelong = ctime.getTime();
		long result = Math.abs(nowtimelong - ctimelong);

		if (result < 60000)// 一分钟内
		{
			long seconds = result / 1000;
//			r = seconds + "秒钟前";
			r = "刚刚";
		} else if (result >= 60000 && result < 3600000)// 一小时内
		{
			long seconds = result / 60000;
			r = seconds + "分钟前";
		} else if (result >= 3600000 && result < 86400000)// 一天内
		{
			long seconds = result / 3600000;
//			r = seconds + "小时前";
			 SimpleDateFormat sdf=new SimpleDateFormat("今天HH:mm");
			r  = sdf.format(ctime);
		} else// 日期格式
		{
//			r = DateTime.formatTime(ctime, format);
			  SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");
			  r= sdf.format(ctime);
		}
		return r;
	}
	
	
	/**
	  * 判断当前日期是星期几
	  * @param  pTime     设置的需要判断的时间  //格式如2012-09-08
	  * @return dayForWeek 判断结果
	  * @Exception 发生异常
	  */
	 private static String getWeek(String pTime) {

		 String Week = "周";
	
		 Calendar c = Calendar.getInstance();
		 try {
	
		     c.setTime(sdf.parse(pTime));
	
		 } catch (ParseException e) {
		     e.printStackTrace();
		 }
		 if (c.get(Calendar.DAY_OF_WEEK) == 1) {
		     Week += "天";
		 }
		 if (c.get(Calendar.DAY_OF_WEEK) == 2) {
		     Week += "一";
		 }
		 if (c.get(Calendar.DAY_OF_WEEK) == 3) {
		     Week += "二";
		 }
		 if (c.get(Calendar.DAY_OF_WEEK) == 4) {
		     Week += "三";
		 }
		 if (c.get(Calendar.DAY_OF_WEEK) == 5) {
		     Week += "四";
		 }
		 if (c.get(Calendar.DAY_OF_WEEK) == 6) {
		     Week += "五";
		 }
		 if (c.get(Calendar.DAY_OF_WEEK) == 7) {
		     Week += "六";
		 }
	
		 
	
		  return Week;
	 }
	
	/**
	 * 时间格式的转换（from String to Data）
	 * @param context
	 * @param string
	 * @return
	 */
	public static Date fromStringToDate(Context context, String string) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
		try {
			return format.parse(string);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 获取当前时间戳
	 * @return
	 */
	public static String getCurrentTimeStamp(){
		
		String currentTime = DateUtil.getCurrentTime();
		String currentTimeStamp = DateUtil.getTimeStamps(currentTime);
		
		return currentTimeStamp;
	}
}
