package ru.web.task.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "config")
@Data
public class Config {
    private MarketConfig market;
    private ServerConfig server;

    @Data
    public static class MarketConfig {
        private String url;
        private String apikey;
    }
    @Data
    public static class ServerConfig {
        private String port;
    }
}