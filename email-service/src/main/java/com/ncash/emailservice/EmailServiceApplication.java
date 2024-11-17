package com.ncash.emailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EmailServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(EmailServiceApplication.class, args);


    }

}
