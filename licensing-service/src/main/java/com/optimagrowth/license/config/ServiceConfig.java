package com.optimagrowth.license.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
public class ServiceConfig{

    private String property;

    @Value("${example.property}")
    private String exampleProperty;

    public String getProperty(){
        return this.property;
    }

    public String getExampleProperty() {
        return exampleProperty;
    }
}