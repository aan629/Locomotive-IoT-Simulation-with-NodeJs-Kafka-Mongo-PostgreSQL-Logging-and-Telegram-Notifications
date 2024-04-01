package com.example.backEndinfoScheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackEndInfoSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndInfoSchedulerApplication.class, args);
	}

}
