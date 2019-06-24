package com.liao.tdoor.util;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 检测链接是否能用
 * @author 廖某某
 * @date 2019/2/21 14:58
 **/
public class TestWsdlConnection {
    /**
     * 根据链接检测是否能连接
     * @param address
     * @return
     */
    public int TestConnection(String address){
        int status=404;
        try {
            URL url=new URL(address);
            HttpURLConnection oc=(HttpURLConnection) url.openConnection();
            oc.setUseCaches(false);
            //设置请求超时
            oc.setConnectTimeout(3000);
            status=oc.getResponseCode();
            if(200==status){
                //200是请求地址顺利连通
                return status;
            }
        }catch (Exception e){
            /*e.printStackTrace();*/
            System.out.println("链接错误2333");
        }
        return status;
    }
}
