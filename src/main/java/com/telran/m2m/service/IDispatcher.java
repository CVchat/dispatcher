package com.telran.m2m.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.MessageChannel;

public interface IDispatcher extends Sink {
    String HIGHER_VALUES = "higher_values";
    String LOWER_VALUES = "lower_values";
    String AVG_VALUES = "avg_values";

    @Output(HIGHER_VALUES)
    MessageChannel higherValues();

    @Output(LOWER_VALUES)
    MessageChannel lowerValues();

    @Output(AVG_VALUES)
    MessageChannel avgValues();

}
