package org.yigit.service;

import org.yigit.enums.AccountType;
import org.yigit.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {
    Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId);
    List<Account> listAllAccount();
}
