package com.jiazm.practice.commons;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期相关utils
 */
@Slf4j
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    // 一天的毫秒数
    public static long ONE_DAY_MILLISECONDS = 1000 * 60 * 60 * 24;

    private static String yyyy_MM_dd = "yyyy-MM-dd";
    private static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    private static String yyyy_MM_dd_HH_mm_ss_SS = "yyyy-MM-dd HH:mm:ss.SSS";
    private static String yyyy_MM_dd_T_HH_mm_ss_SSS = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static String yyyy_MM_dd_T_HH_mm_ss_SSS_Z = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static String yyyy_MM_dd_T_HH_mm_ss_SSS_XXX = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    private static String yyMMddHHmmss = "yyMMddHHmmss";
    private static String yyyyMMdd = "yyyyMMdd";
    private static String HHmmss = "HHmmss";
    private static String HH_mm_ss = "HH:mm:ss";
    private static String yyyy_MM_dd_zh = "yyyy年MM月dd日";
    private static String yyyy_MM_dd_dot = "yyyy.MM.dd";
    private static String yyyy_MM_dd_slash = "yyyy/MM/dd";
    private static String yyyy_MM_dd_HH_mm_ss_slash = "yyyy/MM/dd HH:mm:ss";

    private static String[] dateFormatters = {yyyy_MM_dd_HH_mm_ss, yyyy_MM_dd_T_HH_mm_ss_SSS_XXX, yyyyMMddHHmmss, yyyy_MM_dd, yyyy_MM_dd_slash, yyyy_MM_dd_HH_mm_ss_slash, yyyy_MM_dd_T_HH_mm_ss_SSS_Z, yyyy_MM_dd_T_HH_mm_ss_SSS, yyyy_MM_dd_HH_mm_ss_SS, yyMMddHHmmss, yyyyMMdd, HHmmss, HH_mm_ss, yyyy_MM_dd_zh, yyyy_MM_dd_dot};

    private static String[] FiscalMonth = {"10", "11", "12", "01", "02", "03", "04", "05", "06", "07", "08", "09"};

    private DateUtils() {
    }


    /**
     * 字符串转换为Date类型
     *
     * @param str
     * @return
     */
    public static Date toDate(String str) {
        if (StringUtils.isNotEmpty(str)) {
            try {
                return parseDate(str, dateFormatters);
            } catch (ParseException e) {
                log.warn("DateUtils toDate Error", e);
            }
        }
        return null;
    }

    /**
     * 字符串转换为Date类型
     *
     * @param str
     * @return
     */
    public static Date toDate(String str, String parsePatterns) {
        if (StringUtils.isNotEmpty(str)) {
            try {
                return parseDate(str, parsePatterns);
            } catch (ParseException e) {
                try {
                    return parseDate(str, dateFormatters);
                } catch (ParseException ex) {
                    log.warn("DateUtils toDate Error", e);
                }
            }
        }
        return null;
    }

    /**
     * Date转换为String类型(yyyy-MM-dd)
     *
     * @param date
     * @return
     */
    public static String toYyyy_MM_dd(Date date) {
        return date == null ? null : DateFormatUtils.format(date, yyyy_MM_dd);
    }

    /**
     * Date转换为String类型(yyyyMMdd)
     *
     * @param date
     * @return
     */
    public static String toYyyyMMdd(Date date) {
        return date == null ? null : DateFormatUtils.format(date, yyyyMMdd);
    }

    /**
     * Date转换为String类型(yyMMddHHmmss)
     *
     * @param date
     * @return
     */
    public static String toYyMMddHHmmss(Date date) {
        return date == null ? null : DateFormatUtils.format(date, yyMMddHHmmss);
    }

    /**
     * Date转换为String类型(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     */
    public static String toYyyy_MM_dd_HH_mm_ss(Date date) {
        return date == null ? null : DateFormatUtils.format(date, yyyy_MM_dd_HH_mm_ss);
    }

    /**
     * Date转换为String类型(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     */
    public static String toYyyy_MM_dd_T_HH_mm_ss_SSS(Date date) {
        return date == null ? null : DateFormatUtils.format(date, yyyy_MM_dd_T_HH_mm_ss_SSS);
    }

    /**
     * Date转换为String类型(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     */
    public static String to_yyyy_MM_dd_T_HH_mm_ss_SSS_Z(Date date) {
        return date == null ? null : DateFormatUtils.format(date, yyyy_MM_dd_T_HH_mm_ss_SSS_Z);
    }

    /**
     * Date转换为String类型(yyyy-MM-dd HH:mm:ss)
     *
     * @param date
     * @return
     */
    public static String to_yyyy_MM_dd_T_HH_mm_ss_SSS_XXX(Date date) {
        return date == null ? null : DateFormatUtils.format(date, yyyy_MM_dd_T_HH_mm_ss_SSS_XXX);
    }

    /**
     * Date转换为String类型(HHmmss)
     *
     * @param date
     * @return
     */
    public static String toHHmmss(Date date) {
        return date == null ? null : DateFormatUtils.format(date, HHmmss);
    }

    /**
     * Date转换为String类型(yyyy-MM-dd HH:mm:ss.SSS)
     *
     * @param date
     * @return
     */
    public static String toYyyy_MM_dd_HH_mm_ss_SS(Date date) {
        return date == null ? null : DateFormatUtils.format(date, yyyy_MM_dd_HH_mm_ss_SS);
    }

    /**
     * Date转换为String类型(yyyyMMddHHmmss)
     *
     * @param date
     * @return
     */
    public static String toYyyyMMddHHmmss(Date date) {
        return date == null ? null : DateFormatUtils.format(date, yyyyMMddHHmmss);
    }

    /**
     * Date转换为String类型(yyyy.MM.dd)
     *
     * @param date
     * @return
     */
    public static String toYyyy_MM_dd_dot(Date date) {
        return date == null ? null : DateFormatUtils.format(date, yyyy_MM_dd_dot);

    }

    /**
     * 一天的结束时间
     *
     * @param date
     * @return
     */
    public static Date endTheDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 一天的开始时间
     *
     * @param date
     * @return
     */
    public static Date startTheDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) {
            //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    //闰年
                    timeDistance += 366;
                } else {
                    //不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {
            //不同年
            return day2 - day1;
        }
    }

    /**
     * 计算两个日期相差几个月
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int differentMonth(Date startTime, Date endTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        c.setTime(endTime);
        int endYear = c.get(Calendar.YEAR);
        int endMonth = c.get(Calendar.MONTH);
        if (year == endYear) {
            return endMonth - month + 1;
        } else {
            return endMonth - month + 12 * (endYear - year) + 1;
        }
    }

    /**
     * 计算一个月份的总天数
     *
     * @param date
     * @return
     */
    public static int daysOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 指定日期是否为周末
     *
     * @param date
     * @return
     */
    public static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day == Calendar.SUNDAY || day == Calendar.SATURDAY;
    }

    /**
     * 取得一年的第一天
     *
     * @param date
     * @return
     */
    public static Date getPreviousDayByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 取得某个时间点的前一天0:0:0
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 取得一年的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 取得一月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 取得一月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        return addDays(getFirstDateOfMonth(date), daysOfMonth(date) - 1);
    }

    /**
     * 取得下一月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfNextMonth(Date date) {
        return addDays(getFirstDateOfMonth(date), daysOfMonth(date));
    }

    /**
     * 计算两个日期相差的天数
     *
     * @param d1
     * @param d2
     * @return
     */
    public static int daysOfBetween(Date d1, Date d2) {
        long days1 = d1.getTime() / ONE_DAY_MILLISECONDS;
        long days2 = d2.getTime() / ONE_DAY_MILLISECONDS;
        return (int) (days2 - days1);
    }

    /**
     * 获取年
     *
     * @param date
     * @return int
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @param date
     * @return int
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @param date
     * @return int
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取周
     *
     * @param date
     * @return int
     */
    public static int getWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);

    }

    /**
     * 获取两个日期相差多少秒
     *
     * @param date
     * @return int
     */
    public static long getSubSecond(Date date, Date bigDate) {
        return (bigDate.getTime() - date.getTime()) / 1000;
    }

    /**
     * 获取当前时间秒数
     *
     * @return
     */
    public static int getCurrentSeconds() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 获取倒计时(s)
     *
     * @param endDate 截止时间
     * @return
     */
    public static long getCountdown(Date endDate) {
        return endDate.getTime() - getCurrentSeconds();
    }

    /**
     * 获得联想财年
     *
     * @return
     */
    public static String getFiscalYear(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;

        if (month >= 4) {
            return String.valueOf(year);
        } else {
            return String.valueOf(year - 1);
        }
    }

    /**
     * 获得联想财年-月
     *
     * @return
     */
    public static String getFiscalMonth(Date date) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        int month = cale.get(Calendar.MONTH);

        return FiscalMonth[month];
    }

    /**
     * 计算两个不同时区日期相差值
     *
     * @param time1
     * @param gmt1
     * @param time2
     * @param gmt2
     * @return
     * @auther 梁艳松
     */
    public static long getDatesOffsetWithTimeZone(String time1, String gmt1, String time2, String gmt2) {
        Date date1 = toDate(time1);
        Date date2 = toDate(time2);

        TimeZone timeZone1 = TimeZone.getTimeZone(gmt1);
        TimeZone timeZone2 = TimeZone.getTimeZone(gmt2);

        //计算相差的毫秒数
        long timeZoneOffset = timeZone2.getOffset(date2.getTime()) - timeZone1.getOffset(date1.getTime());
        long millisOffset = date2.getTime() - date1.getTime();
        long ms = millisOffset - timeZoneOffset;

        return ms;
    }

    /**
     * 判断多个时间是否跨月
     *
     * @param date
     * @return int
     */
    public static boolean isCrossMonth(Date... date) {
        if (date != null && date.length > 1) {
            Calendar c = Calendar.getInstance();
            c.setTime(date[0]);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            for (int i = 1; i < date.length; i++) {
                Calendar _c = Calendar.getInstance();
                _c.setTime(date[i]);
                if (year != _c.get(Calendar.YEAR) || month != _c.get(Calendar.MONTH)) {
                    return true;
                }
            }
        }
        return false;
    }

}
