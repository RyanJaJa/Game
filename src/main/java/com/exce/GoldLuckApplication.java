package com.exce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@ComponentScan({"com.exce", "com.exce.controller", "com.exce.service.impl", "com.exce.repository", "com.exce.dto", "com.exce.filter", "com.exce.util"})
@EnableAsync
@EnableScheduling
public class GoldLuckApplication {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(GoldLuckApplication.class, args);
    }
}
