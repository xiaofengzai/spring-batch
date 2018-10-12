package com.enshub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@MapperScan("com.enshub.mapper")
public class SpringBatchApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}
}
