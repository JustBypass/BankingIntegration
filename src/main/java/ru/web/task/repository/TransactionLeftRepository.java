package ru.web.task.repository;


import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.web.task.model.TransactionLeft;

public interface TransactionLeftRepository extends ReactiveCrudRepository<TransactionLeft, Long> {
}