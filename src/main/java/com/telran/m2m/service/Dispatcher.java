package com.telran.m2m.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.m2m.dto.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;


import java.io.IOException;

@EnableBinding(IDispatcher.class)
public class Dispatcher {
    ObjectMapper mapper = new ObjectMapper();
    @Value("${min_value:110}")
    int minValue;
    @Value("${max_value:280}")
    int maxValue;
    @Autowired
    IDispatcher channels;

    @StreamListener(IDispatcher.INPUT)
    void getSensorData(String jsonSensor) throws JsonParseException, JsonMappingException, IOException {
        SensorData sensor = mapper.readValue(jsonSensor,
                SensorData.class);
        if (sensor.number > maxValue) {
            channels.higherValues().send(MessageBuilder
                    .withPayload(jsonSensor).build());
        } else if (sensor.number < minValue) {
            channels.lowerValues().send(MessageBuilder
                    .withPayload(jsonSensor).build());
        }
        channels.avgValues().send(MessageBuilder
                .withPayload(jsonSensor).build());

    }
}
