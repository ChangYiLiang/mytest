package com.chyl.mytest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MytestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MytestApplication.class, args);
	}
}
