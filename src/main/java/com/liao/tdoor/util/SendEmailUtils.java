package com.liao.tdoor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author 廖某某
 * 邮件发送工具类
 *
 */
@Component
public class SendEmailUtils {
    @Autowired
    private JavaMailSender javaMailSender; //注入JavaMailSender
    @Value("${spring.mail.username}")
    private String username; //发送邮件者

    /**
     * 发送邮件
     * @param title 标题
     * @param titleWithName 是否在标题后添加名称
     * @param content 邮件内容
     * @param contentWithName 是否在内容后添加名称
     * @param email 接收者的邮箱
     */
    private void sendNormalEmail(String title,boolean titleWithName,String content,boolean contentWithName,String email){
        String dName="T-door官方";
        MimeMessage mimeMessage=null;
        try{
            mimeMessage=javaMailSender.createMimeMessage(); //创建要发送的消息
            MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,true);
            helper.setFrom(new InternetAddress(username,dName,"UTF-8")); //设置发送者是谁
            helper.setTo(email); //接收者邮箱
            title=titleWithName?title+"-"+dName:title; //标题内容
            helper.setSubject(title); //发送邮件的标题
            if(contentWithName){
                content +="<p style='text-align:right'>"+dName+"</p>";
                content +="<p style='text-align:right'>"+RandomTools.curDate("yyyy-MM-dd HH:mm:ss")+"</p>";
            }
            helper.setText(content,true); //发送的内容 是否为HTML
        }catch (Exception e){
            e.printStackTrace();
        }
        javaMailSender.send(mimeMessage);
    }

    /**
     * 发送注册验证码
     * @param email 接收者的邮箱
     * @param code 验证码
     */
    public void sendRegisterCode(final String email,String code){
        final StringBuffer sb=new StringBuffer(); //实例化一个StringBuffer
        sb.append("<h2>"+"亲爱的"+email+"用户，您好！</h2>").append("<p style='text-align: center; font-size: 24px; font-weight: bold'>您的注册验证码为:"+code+"</p>");
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendNormalEmail("验证码",true,sb.toString(),true,email);
            }
        }).start();
    }

    /**
     * 发送密码找回验证码
     * @param email
     * @param code
     */
    public void sendFindPsdCode(final String email,String code,String nickName){
        final StringBuffer sb=new StringBuffer(); //实例化一个StringBuffer
        sb.append("<h3 style='color:#59b6d7;font-size=14px;'>"+"亲爱的"+nickName+"，您好！</h2>").append("<p style='text-align: center; font-size: 24px; font-weight: bold'>你正在进行找回密码操作，你的找回密码验证码为:"+code+"</p>");
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendNormalEmail("验证码",true,sb.toString(),true,email);
            }
        }).start();
    }
}
