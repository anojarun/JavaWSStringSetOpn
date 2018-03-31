package com.example.api.test.string;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan({"com.example.api.test.string"})


public class SettingApplication {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
