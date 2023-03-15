package com.platform.mockcore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.platform.mockcore.model.mapper")
public class MockCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockCoreApplication.class, args);
    }

}
