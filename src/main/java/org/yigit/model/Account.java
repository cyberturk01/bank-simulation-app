package org.yigit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yigit.enums.AccountStatus;
import org.yigit.enums.AccountType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    private UUID id;
    @NotNull
    @Positive
    private BigDecimal balance;
    @NotNull
    private AccountType accountType;
    private Date creationDate;
    @NotNull
    private Long userId;
    private AccountStatus accountStatus;
}
