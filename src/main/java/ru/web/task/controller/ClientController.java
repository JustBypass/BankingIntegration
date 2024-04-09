package ru.web.task.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.web.task.config.Config;
import ru.web.task.model.Aggregated;
import ru.web.task.model.TransactionLeft;
import ru.web.task.model.TransactionRight;
import ru.web.task.repository.AggregatedRepository;
import ru.web.task.repository.TransactionLeftRepository;
import ru.web.task.repository.TransactionRightRepository;

import java.math.BigInteger;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    AggregatedRepository aggregatedRepository;
    @Autowired
    TransactionLeftRepository transactionLeftRepository;
    @Autowired
    TransactionRightRepository transactionRightRepository;
    @Autowired
    Config config;
    @GetMapping("/config")
    public Mono<Config> config(){
         return Mono.just(config);
    }

    @PutMapping("/task5")
    public Mono<TransactionLeft> task5(@RequestBody TransactionLeft transactionLeft){
        return Mono.defer(()->{
            transactionLeft.setLimDate(LocalDateTime.now());
            return transactionLeftRepository.save(transactionLeft);
        });
    }
    @PutMapping("/transaction_insert")// for external banking service
    public Mono<TransactionRight> transactionInsert(@RequestBody TransactionRight transactionRight){
        return Mono.defer(()->{
            //transactionRight.setLimDate(LocalDateTime.now());
            return transactionRightRepository.save(transactionRight);
        });
    }

    @GetMapping("/task6/{id}")
    public Flux<Aggregated> task6(@PathVariable("id") BigInteger id){
        return aggregatedRepository.getTrueList(id);
    }
    @GetMapping("/{currency}")
    public Flux<Double> task3(@PathVariable("currency")String currency){
        return Flux.just(43.543234533453);
    }
    @GetMapping("/control")
    public Flux<Aggregated> control(){
        return aggregatedRepository.findAll();
    }
}
