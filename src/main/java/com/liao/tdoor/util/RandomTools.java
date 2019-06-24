package com.liao.tdoor.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 随机6位数生成
 * @author 廖某某
 */
public class RandomTools {
    /** 随机6位数 **/
    public static String randomCode(){
        Integer res=(int)((Math.random())*1000000);
        return res+"";
    }
    /*时间捕捉*/
    public static String curDate(String pattern){
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }
    public static String curDate(){
        return curDate("yyyy-MM-dd HH:mm:ss");
    }
}
