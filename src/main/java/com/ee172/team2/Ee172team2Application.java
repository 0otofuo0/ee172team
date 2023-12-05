package com.ee172.team2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.ee172")
public class Ee172team2Application {

    public static void main(String[] args) {
        SpringApplication.run(Ee172team2Application.class, args);
    }

}
