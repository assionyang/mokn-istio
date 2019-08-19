package com.mokn.istio.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.mokn.istio.api.mapper")
@ServletComponentScan
public class ApiApplication {
    public static void main(String[] args){
        SpringApplication.run(ApiApplication.class,args);
    }
}
