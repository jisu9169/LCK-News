package com.sparta.lck_news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class LckNewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LckNewsApplication.class, args);
    }

}
