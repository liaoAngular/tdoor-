package com.liao.tdoor.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author 廖某某
 * @date 2019/4/19 14:10
 * 网页转图片处理类，使用外部CMD
 **/
public class PhantomTools {

    private static final Logger logger= LoggerFactory.getLogger(PhantomTools.class);

    /*private static final String tempPath="C:\\work\\T-door\\webIMG\\post";*/
    private static final String tempPath="/root/webIMG/post";

    /*private static final String shellCommand="C:\\work\\T-door\\externalFile\\phantomjs-2.1.1-windows\\bin\\phantomjs C:\\work\\T-door\\externalFile\\phantomjs-2.1.1-windows\\bin\\rasterize.js ";*/
    private static final String shellCommand="/root/externalFile/phantomjs-2.1.1-windows/bin/phantomjs /root/externalFile/phantomjs-2.1.1-windows/bin/rasterize.js ";

    private String _file;

    private String _size;

    /**
     * 构造截图类
     * @param hash 用于临时文件的目录唯一化
     */
    public PhantomTools(int hash){
        _file=tempPath+hash+".png";
    }

    /**
     * 构造截图类
     * @param hash 用于临时文件的目录唯一化
     * @param size 图片的大小，如800px*600px（此时高度会裁切），或800px（此时 高度最少=宽度*9/16，高度不裁切）
     */
    public PhantomTools(int hash,String size){
        this(hash);
        if(size!=null){
            _size=" "+size;
        }
    }
    /**
     * 将目标网页转为图片字节流
     * @param url 目标网页地址
     * @return 字节流
     * @throws IOException
     */
    public byte[] getByteImg(String url) throws IOException{
        BufferedInputStream in=null;
        ByteArrayOutputStream out=null;
        File file=null;
        byte[] ret=null;
        try{
            if(exeCmd(shellCommand+url+" "+_file+(_size !=null ? _size: ""))){
                file=new File(_file);
                if(file.exists()){
                    out=new ByteArrayOutputStream();
                    byte[] b=new byte[5120];
                    in=new BufferedInputStream(new FileInputStream(file));
                    int n;
                    while ((n=in.read(b,0,5120))!=-1){
                        out.write(b,0,n);
                    }
                    file.delete();
                    ret=out.toByteArray();
                }
            }else {
                ret=new byte[] {};
            }
        }finally {
            try {
                if (out!=null){
                    out.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }try {
                if (in!=null){
                    in.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            if (file!=null && file.exists()){
                file.delete();
            }
        }
        return ret;
    }

    /**
     * 执行cmd命令
     * @param commandStr
     * @return
     */
    private static boolean exeCmd(String commandStr){
        BufferedReader br=null;
        try {
            Process p=Runtime.getRuntime().exec(commandStr);
            if(p.waitFor()!=0 && p.exitValue()==1){
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(br!=null){
                try{
                    br.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
