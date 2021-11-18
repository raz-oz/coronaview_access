package com.rad.ms.corona_view.access;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
public class CoronaViewAccessApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoronaViewAccessApplication.class, args);
    }


}
