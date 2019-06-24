package com.liao.tdoor.util;

import com.liao.tdoor.model.Timestamp;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间判断
 *
 * @author 廖某某
 * @date 2019/2/27 13:11
 **/
public class DateUtils {
    /**
     * 判断是否在当天
     *
     * @param inputJudgeDate
     * @return
     */
    public static boolean isToday(Date inputJudgeDate) {
        boolean flag = false;
        //获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        //定义每天的24H时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date parseBeginTime = null;
        Date parseEndTime = null;
        try {
            parseBeginTime = dateFormat.parse(beginTime);
            parseEndTime = dateFormat.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (inputJudgeDate.after(parseBeginTime) && inputJudgeDate.before(parseEndTime)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 自动刷新时间，并刷新token
     *
     * @param sign
     * @param userId
     */
    public static Map<String,Object> SetTimestamp(String sign, String userId) {
        if (sign.equals("") || sign == null) {
            return null;
        } else {
            Timestamp timestamp = new Timestamp();
            timestamp.setUser_email(userId);
            timestamp.setAccess_time(new Date());
            HashMap<String,Object> map = new HashMap<String ,Object>();
            map.put(sign, timestamp);
            return map;
        }
    }

    /**
     * 判断是否超出7天没有登录(有效时间)
     *
     * @param userId
     * @return
     */
    public static boolean IsValidDate(String userId,Date testTime){
        HashMap map = new HashMap();
        Timestamp t = (Timestamp) map.get(userId);
        boolean convertSuccess = true;
        //时间格式定义
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前时间日期--nowDate
        String nowDate = format.format(new Date());
        //获取7天前时间日期-minDate
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -7);
        String minDate = format.format(calendar.getTime());
        //设置lenient为false，否则SimpleDateFormat会比较宽松地验证日期
        format.setLenient(false);
        //时间转字符串格式
        String strDate = format.format(testTime);
        //判断时间
        if (nowDate.compareTo(strDate) >= 0 && strDate.compareTo(minDate) >= 0) {
            convertSuccess = true;
        } else {
            convertSuccess = false;
        }
        return convertSuccess;
    }
}
