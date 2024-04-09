package ru.web.task.repository;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.web.task.model.TransactionRight;

public interface TransactionRightRepository extends ReactiveCrudRepository<TransactionRight, Long> {

}