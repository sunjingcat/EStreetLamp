package com.lmx.friends.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/10/23.
 */

public class TimeUtils {

    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat DEFAULT_DATE_YMDH = new SimpleDateFormat("yyyy-MM-dd HH");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_DATE_TEXT = new SimpleDateFormat("yyyy年MM月dd日EEEE");
    public static final SimpleDateFormat DATE_FORMAT_DATE_MD = new SimpleDateFormat("MM月dd日");
    public static final SimpleDateFormat DATE_FORMAT_WEEK = new SimpleDateFormat("E");
    public static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_TIME_HM = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_YEAR = new SimpleDateFormat("yyyy");
    public static final SimpleDateFormat DATE_FORMAT_YM = new SimpleDateFormat("yyyy-MM");
    public static final SimpleDateFormat DATE_FORMAT_HOUR_AND_MINUTE = new SimpleDateFormat("HH:mm");
    public static final SimpleDateFormat DATE_FORMAT_DATE_TEXT_AND_TIME = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_MONTH_AND_DAY = new SimpleDateFormat("MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_MONTH = new SimpleDateFormat("MM");


    private TimeUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }


    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return getTime(getCurrentTimeInLong(), dateFormat);
    }


    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return dateFormat.format(new Date(timeInMillis));
    }


    /**
     * 获取当前时间是星期几
     *
     * @param time
     * @return
     */
    public static String getCurrentWeekInString(long time) {

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "日";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return "星期" + mWay;
    }

    //数据月份转换成汉字
    public static String changeDateStr(int m, boolean flag) {
        String str = null;
        String estr = null;
        switch (m) {
            case 1:
                str = "一";
                estr = "January";
                break;
            case 2:
                str = "二";
                estr = "February";
                break;
            case 3:
                str = "三";
                estr = "March";
                break;
            case 4:
                str = "四";
                estr = "April";
                break;
            case 5:
                str = "五";
                estr = "May";
                break;
            case 6:
                str = "六";
                estr = "June";
                break;
            case 7:
                str = "七";
                estr = "July";
                break;
            case 8:
                str = "八";
                estr = "August";
                break;
            case 9:
                str = "九";
                estr = "September";
                break;
            case 10:
                str = "十";
                estr = "October";
                break;
            case 11:
                str = "十一";
                estr = "November";
                break;
            case 12:
                str = "十二";
                estr = "December";
                break;

        }
        if (flag)
            return estr;
        else
            return str;
    }

    /**
     * 时间差值格式化成具体时长
     *
     * @param diff
     * @return
     */
    public static String getDistanceTime(long diff) {

        long diffHours = diff / (60 * 60 * 1000);
        long diffMin = (diff - (diffHours * 60 * 60 * 1000)) / (60 * 1000);
        long diffSec = (diff - (diffHours * 60 * 60 * 1000) - (diffMin * 60 * 1000)) / 1000;

        return zeroFill(diffHours) + ":" + zeroFill(diffMin) + ":" + zeroFill(diffSec);

    }

    /**
     * 时间某位为单个数字补零
     *
     * @param time
     * @return
     */
    public static String zeroFill(long time) {

        if (time < 10 && time >= 0) {
            return "0" + time;
        }
        return String.valueOf(time);

    }

    public static String zeroFill(int time) {

        if (time < 10 && time >= 0) {
            return "0" + time;
        }
        return String.valueOf(time);

    }

    /**
    * 比较时间大小
    * */
    public static boolean getTimeGreater(String time1,String time2){
        if(TextUtils.isEmpty(time1) || TextUtils.isEmpty(time2)){
            return false;
        }else{
            long inTimeLong = Long.valueOf(time1.replaceAll("[-\\s:]",""));
            long outTimeLong = Long.valueOf(time2.replaceAll("[-\\s:]",""));
            return outTimeLong < inTimeLong;
        }

    }
}
