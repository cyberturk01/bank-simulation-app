package org.yigit.service.impl;

import org.springframework.stereotype.Service;
import org.yigit.dto.AccountDTO;
import org.yigit.enums.AccountStatus;
import org.yigit.enums.AccountType;
import org.yigit.repository.AccountRepository;
import org.yigit.service.AccountService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId) {
        //We need to create Account object with builder
        AccountDTO accountDTO = AccountDTO.builder().id(UUID.randomUUID())
                .balance(balance).creationDate(createDate)
                .accountType(accountType).userId(userId).accountStatus(AccountStatus.ACTIVE).build();
        //Save it into database(repository)
        //return the object created
        return accountRepository.save(accountDTO);
    }

    @Override
    public List<AccountDTO> listAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        AccountDTO accountDTO = accountRepository.findById(id);
        accountDTO.setAccountStatus(AccountStatus.DELETED);
//        accountRepository.deleteById(id);
    }

    @Override
    public void activateById(UUID id) {
        AccountDTO accountDTO = accountRepository.findById(id);
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
    }


    @Override
    public AccountDTO findById(UUID id) {
        return accountRepository.findById(id);
    }
}
