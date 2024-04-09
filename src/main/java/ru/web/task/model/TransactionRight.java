package ru.web.task.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("transaction_right")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionRight {
    @Id
    private Long t2Id;
    private Long accountFrom;
    private Long accountTo;
    private LocalDateTime trDate;
    private BigDecimal trSum;
    private String expenseCategory;
}