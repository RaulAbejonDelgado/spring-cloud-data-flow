package com.raul.custom.streamaplication.customstreamapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Transformer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SpringBootApplication
@EnableBinding(Processor.class)
public class CustomStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomStreamApplication.class, args);
	}

	@Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
	public Object tranform(long timestamp) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM//dd hh:mm:ss");
		String date = dateFormat.format(timestamp);
		return date;
	}
}
