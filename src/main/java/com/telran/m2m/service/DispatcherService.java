package com.telran.m2m.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.telran.m2m.dto.RoomData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.integration.support.MessageBuilder;

import java.io.IOException;

@EnableBinding(Dispatcher.class)
public class DispatcherService {
    private ObjectMapper mapper = new ObjectMapper();

    @Value("${temp_min_value:10}")
    double tempMinValue;

    @Value("${temp_max_value:35}")
    double tempMaxValue;

    @Value("${light_min_value:3000}")
    double lightMinValue;

    @Value("${light_max_value:20000}")
    double lightMaxValue;

    @Value("${oxygen_min_value:20.5}")
    double oxygenMinValue;

    @Value("${oxygen_max_value:21.9}")
    double oxygenMaxValue;

    @Value("${co2_min_value:100}")
    double co2MinValue;

    @Value("${co2_max_value:600}")
    double co2MaxValue;

    @Autowired
    Dispatcher channels;

    @StreamListener(Dispatcher.INPUT)
    void getRoomData(String roomPayload) throws IOException {

        RoomData payload = mapper.readValue(roomPayload, RoomData.class);
        System.out.println(payload);

        if (payload.getRoom().getInternalTemperatureC() > tempMaxValue) {
            channels.tempHigherValues().send(MessageBuilder.withPayload(roomPayload).build());
            System.out.println("High Temp");
        } else if (payload.getRoom().getInternalTemperatureC() < tempMaxValue) {
            channels.tempLowerValues().send(MessageBuilder.withPayload(roomPayload).build());
            System.out.println("Low Temp");

        }

        if (payload.getRoom().getLightLumen() > lightMaxValue) {
            channels.lightHigherValues().send(MessageBuilder.withPayload(roomPayload).build());
            System.out.println("High light");

        } else if (payload.getRoom().getLightLumen() < lightMinValue) {
            channels.lightLowerValues().send(MessageBuilder.withPayload(roomPayload).build());
            System.out.println("Low light");

        }

        if (payload.getRoom().getOxygenPercentOfVolume() > oxygenMaxValue) {
            channels.oxygenHigherValues().send(MessageBuilder.withPayload(roomPayload).build());
            System.out.println("High Oxy");

        } else if (payload.getRoom().getOxygenPercentOfVolume() < oxygenMinValue) {
            channels.oxygenLowerValues().send(MessageBuilder.withPayload(roomPayload).build());
            System.out.println("Low Oxy");

        }

        if (payload.getRoom().getCO2PercentOfVolume() > co2MaxValue) {
            channels.co2HigherValues().send(MessageBuilder.withPayload(roomPayload).build());
            System.out.println("High co2");

        } else if (payload.getRoom().getCO2PercentOfVolume() < co2MinValue) {
            channels.co2LowerValues().send(MessageBuilder.withPayload(roomPayload).build());
            System.out.println("Low co2");

        }
        if (payload.getRoom().isSmoke()) {
            channels.smokeSensor().send(MessageBuilder.withPayload(roomPayload).build());
            System.out.println("smoke");

        }
        channels.monitoringDashboard().send(MessageBuilder.withPayload(roomPayload).build());
        System.out.println("monitoring");
    }
}
