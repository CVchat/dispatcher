package com.telran.m2m.service;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.MessageChannel;

public interface Dispatcher extends Sink {
    String TEMP_HIGHER_VALUES = "temp_higher_values";
    String TEMP_LOWER_VALUES = "temp_lower_values";

    String LIGHT_HIGHER_VALUES = "light_higher_values";
    String LIGHT_LOWER_VALUES = "light_lower_values";

    String OXYGEN_HIGHER_VALUES = "oxygen_higher_values";
    String OXYGEN_LOWER_VALUES = "oxygen_lower_values";

    String C02_HIGHER_VALUES = "co2_higher_values";
    String C02_LOWER_VALUES = "co2_lower_values";

    String SMOKE_SENSOR = "smoke_sensor";

    String MONITORING_DASHBOARD = "monitoring_dashboard";

    @Output(TEMP_HIGHER_VALUES)
    MessageChannel tempHigherValues();

    @Output(TEMP_LOWER_VALUES)
    MessageChannel tempLowerValues();

    @Output(LIGHT_HIGHER_VALUES)
    MessageChannel lightHigherValues();

    @Output(LIGHT_LOWER_VALUES)
    MessageChannel lightLowerValues();

    @Output(OXYGEN_HIGHER_VALUES)
    MessageChannel oxygenHigherValues();

    @Output(OXYGEN_LOWER_VALUES)
    MessageChannel oxygenLowerValues();

    @Output(C02_HIGHER_VALUES)
    MessageChannel co2HigherValues();

    @Output(C02_LOWER_VALUES)
    MessageChannel co2LowerValues();

    @Output(SMOKE_SENSOR)
    MessageChannel smokeSensor();

    @Output(MONITORING_DASHBOARD)
    MessageChannel monitoringDashboard();

}
