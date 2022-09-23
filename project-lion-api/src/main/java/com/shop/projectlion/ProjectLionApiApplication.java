package com.shop.projectlion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class ProjectLionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectLionApiApplication.class, args);
    }

}
