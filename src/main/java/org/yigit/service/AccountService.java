package org.yigit.service;

import org.yigit.enums.AccountType;
import org.yigit.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId);
    List<Account> listAllAccount();

    void deleteById(UUID id);
    void activateById(UUID id);
    Account findById(UUID id);
}
