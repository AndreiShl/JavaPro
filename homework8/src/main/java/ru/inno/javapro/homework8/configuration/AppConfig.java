package ru.inno.javapro.homework8.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
@EnableScheduling
public class AppConfig {

    @Bean
    public RestClient restClient(@Value("${payment-limit.payment-service-url}") String paymentServiceUrl) {
        return RestClient.builder().baseUrl(paymentServiceUrl).build();
    }
}
