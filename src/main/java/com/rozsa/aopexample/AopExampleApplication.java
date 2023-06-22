package com.rozsa.aopexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AopExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopExampleApplication.class, args);
    }

}
