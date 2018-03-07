package com.hna.scheduler.configurations;

import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class TestApplicationConfig {

    @Value("${spring.datasource.driverClassName}")
    private String driverClass;

    @PostConstruct
    public void tweakH2CompatibilityMode() {
        if ("org.h2.Driver".equals(this.driverClass)) {
            Mode mode = Mode.getInstance("MYSQL");

            mode.convertInsertNullToZero = false;
        }
    }
}