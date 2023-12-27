package org.yigit.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.yigit.enums.AccountStatus;
import org.yigit.enums.AccountType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(columnDefinition = "TIMESTAMP")
    private Date creationDate;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
}
