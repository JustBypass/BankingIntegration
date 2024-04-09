package ru.web.task.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Configuration
@Import({Config.class})
@EnableWebFlux
public class AppConfig {
    @Bean
    public WebClient webClient(){
        return WebClient.create();
    }

    @Bean
    public Scheduler scheduler() {
        return Schedulers.parallel(); // Use a parallel scheduler for scheduling tasks
    }
    @Autowired
    Config configVars;

    @Bean
    public Disposable someTaskScheduler(WebClient webClient) {
        return Flux.interval(Duration.ofMinutes(1)) // run every minute
                .publishOn(Schedulers.boundedElastic())
                .onBackpressureDrop()
                .concatMap(__ -> Mono.defer(() -> {
                    // process your tasks below
                    System.out.println("Scheduled");
                    // Выполнение запроса с помощью WebClient
                    return webClient.get().uri(configVars.getMarket().getUrl())
                            .retrieve()
                            .bodyToMono(String.class)
                            .doOnSuccess(response -> {
                                // Обработка ответа
                                System.out.println("Response: " + response);
                            })
                            .thenReturn("completed");
                }), 0)
                .subscribe();
    }

/*    @Component
    @Profile("dev")
    public class DevDatasourceConfig implements DatasourceConfig {
        @Override
        public void setup() {
            System.out.println("Setting up datasource for DEV environment. ");
        }
    }*/
}
