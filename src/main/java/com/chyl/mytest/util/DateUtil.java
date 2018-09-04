package com.chyl.mytest.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式的一些处理方法
 *
 * @author jorden
 */
public class DateUtil {

    /**
     * 获取指定日期的时间
     * @param time
     * @param day
     * @return
     */
    public static long getMothSomeDayTime(long time,int day){
        Date date = DateUtil.timeMillisToDate(time);
        Date assignDate = new Date(date.getYear(),date.getMonth(),day);
        // 当前月指定日期时间
        long assignTime = assignDate.getTime() / 1000;
        // 当前月最后一天时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH,date.getMonth()-1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long calendarTime = calendar.getTime().getTime() / 1000;

        if(assignTime > calendarTime){
            return calendarTime;
        }
        return assignTime;
    }

    /**
     * 计算两个时间戳之间相差的天数(2.1=3)，适于计头不计尾
     *
     * @param sDate 开始时间
     * @param eDate 结束时间
     * @return
     */
    public static int getTimeDifference(long sDate, long eDate) {
        return ((int) ((eDate - sDate) / 60 / 60 / 24)) + 1;
    }

    /**
     * 计算两个时间戳之间相差的天数(2.1=2)
     * @param sDate
     * @param eDate
     * @return
     */
    public static int getDaysDifference(long sDate, long eDate) {
        return (int) ((eDate - sDate) / 60 / 60 / 24);
    }

    public static long getMorningTime(long time) throws ParseException {
        String str = formatDateByPattern(time,"yyyyMMdd");
        return dateTimeStampyyyyMMdd(str);
    }

    /**
     * 获取指定日期n个月之后的日期
     *
     * @param date 指定日期
     * @param n    月数
     * @return
     * @throws ParseException
     */
    public static long getMonthBefore(long date, int n) throws ParseException {
        if(n == 0){
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.timeMillisToDate(date));
        calendar.add(Calendar.MONTH, n);
        return calendar.getTime().getTime() / 1000;
    }

    /**
     * 获取指定日期n天之后的日期
     *
     * @param date 指定日期
     * @param n    天
     * @return
     * @throws ParseException
     */
    public static long getDaysBefore(long date, int n) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.timeMillisToDate(date));
        calendar.add(Calendar.DAY_OF_MONTH, n);
        return calendar.getTime().getTime() / 1000;
    }

    /**
     * 获取指定日期n小时之后的日期
     *
     * @param date 指定日期
     * @param n    天
     * @return
     * @throws ParseException
     */
    public static long getHoursBefore(long date, int n) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtil.timeMillisToDate(date));
        calendar.add(Calendar.HOUR, n);
        return calendar.getTime().getTime() / 1000;
    }

    /**
     * 将date转为unix时间戳
     *
     * @param date
     * @return
     */
    public static long dateFormatUnix(Date date) {
        return (date.getTime()) / 1000;
    }

    /**
     * 将String类型时间转成时间戳
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static long dateTimeStamp(String dateString) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateString);
        return (date.getTime()) / 1000;
    }

    /**
     * 将String类型时间转成时间戳 yyyyMMdd
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static long dateTimeStampyyyyMMdd(String dateString) throws ParseException {
        Date date = new SimpleDateFormat("yyyyMMdd").parse(dateString);
        return (date.getTime()) / 1000;
    }

    /**
     * 将String类型时间转成时间戳yyyy-MM-dd
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static long dateTimeStampyyyyMMdd1(String dateString) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        return (date.getTime()) / 1000;
    }

    /**
     * 获取系统currentTimeMillis（10位） unix时间戳
     *
     * @return int数值
     */
    public static long getUnixTime() {
        return (System.currentTimeMillis()) / 1000;
    }


    /**
     * 将unix时间戳转为date
     *
     * @param currentTimeMillis
     * @return
     */
    public static Date timeMillisToDate(long currentTimeMillis) {
        Date date = new Date(currentTimeMillis * 1000);
        return date;
    }

    /**
     * currentTimeMillis（10位）转换为指定格式的字符串日期
     * @param1  currentTimeMillis
     * @param2   指定日期格式
     * @return str
     */
    public static String timeMillisToStr(long currentTimeMillis, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date = new Date(currentTimeMillis*1000);
        return sf.format(date);
    }

    /**
     * 获取当前时间
     *
     * @return 自定义字符串日期格式 ，例如 yyyy-MM-dd
     */
    public static String getCurrentDate(String format) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /***
     *	格式化时间
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * 将unix时间戳转成指定格式时间
     * @param time
     * @param dateFormat
     * @return
     */
    public static String formatDateByPattern(long time, String dateFormat) {
        Date date = timeMillisToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * 获取月份的第一天
     * @param format-日期格式：如yyyy/MM/dd
     * @param i-0表示当月第一天，1表示下月第一天....
     * @return
     */
    public static String getMonthFirstDay(String format, int i){
        Calendar cale = null;
        cale = Calendar.getInstance();
        SimpleDateFormat sformat = new SimpleDateFormat(format);
        String firstday;
        cale.add(Calendar.MONTH, i);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = sformat.format(cale.getTime());
        return firstday;
    }

    /**
     * 获取月份最后一天
     * @return
     */
    public static String getMonthLastDay(String format,int i){
        SimpleDateFormat sformat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.MONTH, month+i);
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date strDateTo = calendar.getTime();
        String lastDay = sformat.format(strDateTo);
        return lastDay;
    }

    /**
     * 将日期格式的字符串转换为int
     *
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static int convert2int(String date, String format) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return (int)(sf.parse(date).getTime()/1000);
    }

    public static long convert2Long(String date, String format) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return (long)(sf.parse(date).getTime()/1000);
    }

    public static Date convert2Date(String date, String format) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        return sf.parse(date);
    }

    public static String convert2String(long time, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date = new Date(time*1000);
        return sf.format(date);
    }

    /***
     * 根据日期获取月份
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMonthByDate(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sf1 = new SimpleDateFormat("M");
        return sf1.format(sf.parse(date));
    }

    /***
     * 根据日期获取月份
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getYearByDate(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy");
        return sf1.format(sf.parse(date));
    }

    /***
     * 根据日期获取月周期
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMonthCycleByDate(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sf1 = new SimpleDateFormat("MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sf.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDayOfMonth = calendar.getTime();
        return sf.format(firstDayOfMonth) + "-" + sf1.format(lastDayOfMonth);
    }

    /***
     * 根据日期获取月第一天
     * @param date
     * @return
     * @throws ParseException
     */
    public static Long getMonthFirstDayByDate(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sf.parse(date));
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();
        return firstDayOfMonth.getTime()/1000;
    }

    /***
     * 根据日期获取下月第一天
     * @param date
     * @return
     * @throws ParseException
     */
    public static Long getMonthLastDayByDate(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sf.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 2);
        Date lastDayOfMonth = calendar.getTime();
        return lastDayOfMonth.getTime()/1000;
    }

    /***
     * 根据日期获取上月日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getLastMonthDateByDate(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sf.parse(date));
        calendar.add(Calendar.MONTH, -1);
        return sf.format(calendar.getTime());
    }

    /***
     * 根据日期获取年月
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getYearAndMonthByDate(String date) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy/MM/");
        return sf1.format( sf.parse(date));
    }

    /***
     * 判断一个时间是不是昨天
     */
    public static boolean isYesterday(Date date) {
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - dateCalendar.get(Calendar.DAY_OF_YEAR) == 1;
    }
}
