package com.application.image.lib_ui.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by      Android studio
 *
 * @author :chenzheming (from Center Of Wuhan)
 * 创建时间 :2020/9/28$
 * 更新时间 :
 * Version :1.0
 * 功能描述 :
 **/
public class TimeUtil {
    //时间格式
    public final static String FORMAT_TIME = "HH:mm";
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    public final static String FORMAT_YEAR_MONTH_DAY = "yyyy/MM/dd";
    public final static String FORMAT_HOUR_MINUTES_SECOND = "HH:mm:ss";
    public final static String FORMAT_DATE_TIME_SECOND = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_MONTH_DAY_TIME = "MM-dd HH:mm";
    public final static String FORMAT_DATE = "yyyy.MM.dd";

    /**
     * 根据当前时间戳转换成年月日 yyyy/MM/dd
     *
     * @param timestamp
     * @return
     */
    public static String getTimeYearMonthDay(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
        return format.format(new Date(timestamp));
    }

    public static final String YMDHMS = "yyyy-MM-dd HH-mm-ss";
    public static final String YMD = "yyyy-MM-dd";

    public static boolean isSameDay(String timeStamp1, String timeStamp2) {

        String sdfTime1 = getSdfTime(timeStamp1, YMD);
        String sdfTime2 = getSdfTime(timeStamp2, YMD);
        if(sdfTime1.equals(sdfTime2)){
            return true;
        }else{
            return false;
        }
    }


    public static String getChinaTime(String timeStamp) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
        return sdr.format(new Date(Long.valueOf(timeStamp + "000")));
    }


    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *  sdfForm "yyyy-MM-dd-HH-mm-ss"
     */
    public static String getSdfTime(String timeStamp, String sdfForm) {
        SimpleDateFormat sdr = new SimpleDateFormat(sdfForm);

        return sdr.format(new Date(Long.valueOf(timeStamp + "000")));
    }


    /**
     * 根据当前时间戳转换成时分秒
     *
     * @param timestamp
     * @return
     */
    public static String getTimeHourMinutesSecond(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_HOUR_MINUTES_SECOND);
        return format.format(new Date(timestamp));
    }

    /**
     * 根据当前时间戳转换成时分
     *
     * @param timestamp
     * @return
     */
    public static String getTimeHourMinutes(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_TIME);
        return format.format(new Date(timestamp));
    }

    /**
     * 根据当前时间戳获得是星期几
     *
     * @param timestamp 时间戳
     * @return
     */
    public static String getTimeWeek(long timestamp) {
        Date date = new Date(timestamp);
        String Week = "";
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int wek = c.get(Calendar.DAY_OF_WEEK);
        if (wek == 1) {
            Week += "星期日";
        }
        if (wek == 2) {
            Week += "星期一";
        }
        if (wek == 3) {
            Week += "星期二";
        }
        if (wek == 4) {
            Week += "星期三";
        }
        if (wek == 5) {
            Week += "星期四";
        }
        if (wek == 6) {
            Week += "星期五";
        }
        if (wek == 7) {
            Week += "星期六";
        }
        return Week;
    }
}
