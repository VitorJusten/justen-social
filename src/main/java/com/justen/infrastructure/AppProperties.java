package com.justen.infrastructure;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 
 * @Author GitHub - VitorJusten
 * @ProjectName justen-social
 * @Year 2026
 *
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Cors cors = new Cors();

    @Data
    public static class Cors {
        private List<String> allowedOrigins = List.of();
    }

}