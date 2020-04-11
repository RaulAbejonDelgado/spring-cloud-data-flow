package com.raul.custom.processor.customprocessor;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.Transformer;

import org.springframework.cloud.stream.messaging.Processor;

@EnableBinding(Processor.class)
public class CelciusConverterProcessorConfiguration {

    @Transformer(inputChannel = Processor.INPUT , outputChannel = Processor.OUTPUT)
    public int convertToCelsius(String payload) {
        int fahrenheitTemperature = Integer.parseInt(payload);
        System.out.println("The input value as "+(fahrenheitTemperature));

        return (fahrenheitTemperature-32)*5/9;
    }
}
