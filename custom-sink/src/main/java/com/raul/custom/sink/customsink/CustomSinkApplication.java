package com.raul.custom.sink.customsink;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding(Sink.class)
public class CustomSinkApplication {

	private static Logger logger = LoggerFactory.getLogger(CustomSinkApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CustomSinkApplication.class, args);
	}


	@StreamListener(Sink.INPUT)
	public void loogerSink(String date) {
		logger.info("Received: "+ date);
	}

}
