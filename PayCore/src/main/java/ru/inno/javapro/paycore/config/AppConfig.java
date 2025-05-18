package ru.inno.javapro.paycore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@Slf4j
public class AppConfig {

    @Bean
    public RestClient restClient(@Value("${pay-service.user-product-url}") String userProductUrl) {
        log.info("userProductUrl: {}", userProductUrl);
        return RestClient
                .builder()
                .baseUrl(userProductUrl)
                .build();
    }
}
