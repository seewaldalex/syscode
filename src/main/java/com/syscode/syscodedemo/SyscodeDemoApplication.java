package com.syscode.syscodedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SyscodeDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyscodeDemoApplication.class, args);
	}

}
