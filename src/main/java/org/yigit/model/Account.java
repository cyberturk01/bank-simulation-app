package org.yigit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.yigit.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class Account {
    private UUID id;
    private BigDecimal balance;
    private AccountType accountType;
    private Date creationDate;
    private Long userId;
}
