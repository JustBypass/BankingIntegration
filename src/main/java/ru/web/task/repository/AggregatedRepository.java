package ru.web.task.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.web.task.model.Aggregated;

import java.math.BigInteger;

public interface AggregatedRepository extends ReactiveCrudRepository<Aggregated, Long> {
    @Query("SELECT * FROM aggregated a WHERE a.limit_exceeded = 'TRUE' AND a.account_to = $1")
    Flux<Aggregated> getTrueList(BigInteger account_to);
}