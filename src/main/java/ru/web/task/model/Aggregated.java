package ru.web.task.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("aggregated")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aggregated {
    private Long accountFrom;
    private Long accountTo;
    private String limitCurrencyShortname;
    private LocalDateTime trDate;
    private BigDecimal trSum;
    private Boolean limitExceeded;
}