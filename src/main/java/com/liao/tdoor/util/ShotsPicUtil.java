package com.liao.tdoor.util;

import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 获取网页截图
 * @author 廖某某
 * @date 2019/2/21 10:26
 **/
public class ShotsPicUtil extends JPanel {
    private static final long serialVersionUID=1L;
    //行分隔符
    final static public String LS=System.getProperty("line.separator","\n");
    //文件分隔符
    final static public String FS=System.getProperty("file.separator","\\");
    //以JavaScript脚本获取网页全屏后
    final static StringBuffer jsDimension;

    private static final Lock lock=new ReentrantLock();

    private static final Condition finishCondition=lock.newCondition();

    static {
        jsDimension=new StringBuffer();
        jsDimension.append("var width=0;").append(LS);
        jsDimension.append("var height=0;").append(LS);
        jsDimension.append("if(document.documentElement){").append(LS);
        jsDimension.append("width=Math.max(width,document.documentElement.scrollWidth);").append(LS);
        jsDimension.append("height=Math.max(height,document.documentElement.scrollHeight);").append(LS);
        jsDimension.append("}").append(LS);
        jsDimension.append("if(self.innerWidth){").append(LS);
        jsDimension.append("width=Math.max(width,self.innerWidth);").append(LS);
        jsDimension.append("height=Math.max(height,self.innerHeight);").append(LS);
        jsDimension.append("}").append(LS);
        jsDimension.append("if(document.body.scrollWidth){").append(LS);
        jsDimension.append("width=Math.max(width,document.body.scrollWidth);").append(LS);
        jsDimension.append("height=Math.max(width,document.body.scrollHeight);").append(LS);
        jsDimension.append("}").append(LS);
        jsDimension.append("return width + ':'  +  height;");
    }
    public static synchronized boolean GeneratePng(final String url){

        return false;
    }

    public  ShotsPicUtil(String url,int maxWidth,int maxHeight,String imgName){
        super(new BorderLayout());
        JPanel webBrowserPanel=new JPanel(new BorderLayout());
        final String fileName=imgName+".jpg";
        final JWebBrowser webBrowser=new JWebBrowser(null);
        webBrowser.setBarsVisible(false);
        webBrowser.navigate(url);
        webBrowserPanel.add(webBrowser,BorderLayout.CENTER);
        add(webBrowserPanel,BorderLayout.CENTER);

        JPanel panel= new JPanel(new FlowLayout(FlowLayout.CENTER,4,4));
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            //监听加载进度
            public void loadingProgressChanged(WebBrowserEvent e) {
                //当加载完毕
                if(e.getWebBrowser().getLoadingProgress()==100){
                    //动态执行js，最终返回网页的实际宽度和高度
                    String result=(String) webBrowser.executeJavascriptWithResult(jsDimension.toString());
                    System.out.println("获取的结果为："+result);
                    int index=result==null?-1:result.indexOf(":");
                    NativeComponent nativeComponent=webBrowser.getNativeComponent();
                    Dimension originalSize=nativeComponent.getSize();
                    Dimension imageSize=new Dimension(Integer.parseInt(result.substring(0,index))
                            ,Integer.parseInt(result.substring(index+1)));
                    imageSize.width=Math.max(originalSize.width,imageSize.width);
                    imageSize.height=Math.max(originalSize.height,imageSize.height);
                    System.out.println("宽度:"+imageSize.width);
                    System.out.println("高度:"+imageSize.height);
                    nativeComponent.setSize(imageSize);
                    BufferedImage image=new BufferedImage(imageSize.width,imageSize.height,BufferedImage.TYPE_INT_RGB);
                    nativeComponent.paintComponent(image);
                    nativeComponent.setSize(originalSize);
                    //当网页超出目标大小时
                    if(imageSize.width>maxWidth || imageSize.height>maxHeight){
                        //截图部分
                        image=image.getSubimage(0,0,maxWidth,maxHeight);
                        //缩略图
                        /*int width=image.getWidth(),height=image.getHeight();
                        AffineTransform tx=new AffineTransform();
                        tx.scale((double)maxWidth/width,(double)maxHeight/height);
                        AffineTransformOp op=new AffineTransformOp(tx,AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                        //缩小
                        image=op.filter(image,null);*/
                    }
                    try {
                        //输出图像
                        ImageIO.write(image,"jpg",new File("C:\\work\\T-door\\img\\"+fileName));
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                    //退出操作
                    System.exit(0);
                }
            }
        });
        add(panel,BorderLayout.SOUTH);
    }
    public static void main(String [] args){
        NativeInterface.open();
        String url="https://fanyi.baidu.com/#zh/en/%E7%BC%A9%E7%95%A5%E5%9B%BE";
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame=new JFrame("以DJ组件保存指定的网页截图");
                frame.getContentPane().add(
                        new ShotsPicUtil(url,
                                640,480,"5201314"),
                        BorderLayout.CENTER
                );
                frame.setSize(800,600);
                //初始化，但不显示
                frame.invalidate();
                frame.pack();
                frame.setVisible(false);
            }
        });
        NativeInterface.runEventPump();
    }
}
