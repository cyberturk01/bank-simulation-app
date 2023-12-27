package org.yigit.service;

import org.yigit.dto.AccountDTO;
import org.yigit.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {
    void createNewAccount(AccountDTO accountDTO);
    List<AccountDTO> listAllAccount();

    void deleteById(Long id);
    void activateById(Long id);
    AccountDTO findById(Long id);
    List<AccountDTO> listAllActiveAccount();

    void updateAccount(AccountDTO accountDTO);
}
