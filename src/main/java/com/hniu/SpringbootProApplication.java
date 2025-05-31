package com.hniu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hniu.mapper")
public class SpringbootProApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootProApplication.class, args);
    }

}
