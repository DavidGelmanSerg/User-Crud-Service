package ru.gelman.user_crud_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class UserCrudServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCrudServiceApplication.class, args);
    }

}
