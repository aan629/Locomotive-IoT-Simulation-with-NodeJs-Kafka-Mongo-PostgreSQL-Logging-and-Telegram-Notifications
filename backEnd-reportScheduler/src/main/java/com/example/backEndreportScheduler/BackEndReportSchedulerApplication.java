package com.example.backEndreportScheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackEndReportSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEndReportSchedulerApplication.class, args);
	}

}
