package com.seeleaf.guitarbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.seeleaf.guitarbackend.mapper")
public class GuitarBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuitarBackendApplication.class, args);
    }

}
