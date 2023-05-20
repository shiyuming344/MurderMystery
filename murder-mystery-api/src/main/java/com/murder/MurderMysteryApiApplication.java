package com.murder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.murder.mapper")
public class MurderMysteryApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MurderMysteryApiApplication.class, args);
    }

}
