package com.raul.custom.task.tasksystemlogging;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableTask
public class TaskSystemLoggingApplication {

	@Bean
	public MyTaskSystemLoggingApplication myTaskSystemLoggingApplication(){
		return new MyTaskSystemLoggingApplication();
	}

	public static void main(String[] args) {
		SpringApplication.run(TaskSystemLoggingApplication.class, args);
	}

	public static class MyTaskSystemLoggingApplication implements ApplicationRunner {
		@Override
		public void run(ApplicationArguments args) throws Exception {
			System.out.println("Run method : ".concat(new SimpleDateFormat().format(new Date())));
		}
	}

}
