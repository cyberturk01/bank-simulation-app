package org.yigit.dto;

import lombok.*;
import org.yigit.enums.AccountStatus;
import org.yigit.enums.AccountType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
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
