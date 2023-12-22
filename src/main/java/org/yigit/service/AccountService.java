package org.yigit.service;

import org.yigit.dto.AccountDTO;
import org.yigit.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountDTO createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId);
    List<AccountDTO> listAllAccount();

    void deleteById(UUID id);
    void activateById(UUID id);
    AccountDTO findById(UUID id);
}
