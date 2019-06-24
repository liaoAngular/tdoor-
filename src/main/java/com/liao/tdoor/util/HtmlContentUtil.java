package com.liao.tdoor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取网页标题
 * @author 廖某某
 * @date 2019/2/21 13:57
 **/
public class HtmlContentUtil {
    /**
     * 根据网址返回网页源码
     * @param url
     * @return
     */
    public String getAllHtml(String url){
        URL htmlUrl;
        StringBuffer sb=new StringBuffer();
        try{
            htmlUrl=new URL(url);
            //读取全篇内容
            BufferedReader in=new BufferedReader(new InputStreamReader(htmlUrl.openStream(),"utf-8"));
            String temp;
            while ((temp=in.readLine())!=null){
                sb.append(temp);
            }
            in.close();
        }catch (MalformedURLException e){
            System.out.println("URL格式有问题！请仔细输入");
        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 从HTML源码中获取标题
     * @param htmlSource
     * @return
     */
    public String getTitle(String htmlSource){
        List<String> list=new ArrayList<String>();
        String title="";
        //源码中标题正则表达式
        Pattern pattern=Pattern.compile("<title>.*?</title>");
        Matcher matcher=pattern.matcher(htmlSource);
        while (matcher.find()){
            //将符合的字符记录
            list.add(matcher.group());
        }
        for(int i=0;i<list.size();i++){
            title=title+list.get(i);
        }
        return outTag(title);
    }
    /**
     * 去掉标签
     * @param titleSource
     * @return
     */
    public String outTag(String titleSource){
        return titleSource.replaceAll("<.*?>","");
    }

    /**
     * 获取网页标题--主体方法
     * @param url
     * @return
     */
    public String GetHtmlTitle(String url){
        System.out.println("/n------------开始读取网页"+url+"-------------");
        String htmlSource="";
        htmlSource=getAllHtml(url);
        System.out.println("------------读取网页(" + url + ")结束-----------/n");
        System.out.println("------------分析(" + url + ")结果如下-----------/n");
        String title = getTitle(htmlSource);
        return title;
    }

    public static void main(String [] args){
        String htmlUrl="https://www.baidu.com123";
        TestWsdlConnection t=new TestWsdlConnection();
        int code=t.TestConnection(htmlUrl);
        if(code==200){
            HtmlContentUtil util=new HtmlContentUtil();
            String title=util.GetHtmlTitle(htmlUrl);
            System.out.println("网页标题为:"+title);
        }else {
            System.out.println("链接错误-----");
        }
    }
}
