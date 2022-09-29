package com.example.mywebquizengine;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class MywebquizengineApplication {

    public static ApplicationContext ctx;

    public static void main(String[] args) {
        SpringApplication.run(MywebquizengineApplication.class, args);
    }

    /**
     * Make Spring inject the application context
     * and save it on a static variable,
     * so that it can be accessed from any point in the application.
     */
    @Autowired
    private void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }


}
