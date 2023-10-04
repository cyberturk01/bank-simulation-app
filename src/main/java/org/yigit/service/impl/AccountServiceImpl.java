package org.yigit.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.yigit.enums.AccountStatus;
import org.yigit.enums.AccountType;
import org.yigit.model.Account;
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
    public Account createNewAccount(BigDecimal balance, Date createDate, AccountType accountType, Long userId) {
        //We need to create Account object with builder
        Account account = Account.builder().id(UUID.randomUUID())
                .balance(balance).creationDate(createDate)
                .accountType(accountType).userId(userId).accountStatus(AccountStatus.ACTIVE).build();
        //Save it into database(repository)
        //return the object created
        return accountRepository.save(account);
    }

    @Override
    public List<Account> listAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteById(UUID id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account findById(UUID id) {
        return accountRepository.findById(id);
    }
}
