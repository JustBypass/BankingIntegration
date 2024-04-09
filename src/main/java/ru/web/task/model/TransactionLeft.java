package ru.web.task.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("transaction_left")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionLeft {
    private Long accountFrom;
    private Long accountTo;
    private LocalDateTime limDate;
    private String limitCurrencyShortname;
    private BigDecimal lim;
    private String expenseCategory;
}
