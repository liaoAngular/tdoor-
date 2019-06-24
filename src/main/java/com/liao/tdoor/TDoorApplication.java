package com.liao.tdoor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.liao.tdoor.dao")
public class TDoorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TDoorApplication.class, args);
    }

}

