package com.liao.tdoor.unitTest;

import com.liao.tdoor.util.DateUtils;
import com.liao.tdoor.util.PhantomTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author 廖某某
 * @date 2019/3/16 15:00
 **/
public class ToolClassTest {
    @Test
    public void Test1(){
        String string = "2016-10-24 21:59:06";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();
        try {
            date=sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        boolean result=DateUtils.IsValidDate("123",date);
        if(result){
            System.out.println("没有过期");
        }else {
            System.out.println("已经过期");
        }
    }
    /*@Test*/
    public void testScreenShot() throws IOException {
        PhantomTools tools=new PhantomTools(1,"800px*600px");
        byte[] s= tools.getByteImg("https://www.tencent.com/zh-cn/index.html");
        FileOutputStream out=new FileOutputStream("C:\\work\\T-door\\webIMG\\post\\test.png");
        out.write(s);
        out.close();
    }
}
