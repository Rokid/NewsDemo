package com.rokid.news;

import java.util.Calendar;

/**
 * Created by fanfeng on 2017/6/6.
 */

public class HelloUtils {

    private static final String GOOD_MORING = "早上好";
    private static final String GOOD_AFTERNOON = "下午好";
    private static final String GOOD_EVENING = "晚上好";

    public static String hello(){
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + 1;
        if ( hour>=6 && hour<= 12){
            return GOOD_MORING;
        }else if (hour>12 && hour<=18){
            return GOOD_AFTERNOON;
        }else if ((hour>18 && hour<=24) || (hour>=0 && hour<6)){
            return GOOD_EVENING;
        }else {
            Logger.d("invalidate hour " + hour);
            return "";
        }
    }
}
