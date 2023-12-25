package org.yigit.service.impl;

import org.springframework.stereotype.Service;
import org.yigit.dto.AccountDTO;
import org.yigit.entity.Account;
import org.yigit.enums.AccountStatus;
import org.yigit.enums.AccountType;
import org.yigit.mapper.AccountMapper;
import org.yigit.repository.AccountRepository;
import org.yigit.service.AccountService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public void createNewAccount(AccountDTO accountDTO) {
        //We need to create Account object with builder
        accountDTO.setCreationDate(new Date());
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
        //Save it into database(repository)
        //return the object created
        accountRepository.save(accountMapper.convertToEntity(accountDTO));
    }

    @Override
    public List<AccountDTO> listAllAccount() {
        //We are getting the all list
        List<Account> list= accountRepository.findAll();
        //we are converting entity to dto and return as DTO
        return list.stream().map(accountMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        //find the account object id
        Account account = accountRepository.findById(id).get();
        //set status to deleted
        account.setAccountStatus(AccountStatus.DELETED);
//        accountRepository.deleteById(id);
        //save the latest state
        accountRepository.save(account);
    }

    @Override
    public void activateById(Long id) {
        Account account = accountRepository.findById(id).get();
        account.setAccountStatus(AccountStatus.ACTIVE);
        accountRepository.save(account);
    }


    @Override
    public AccountDTO findById(Long id) {
        //find the account entity based on id, then convert to dto then return it
        return accountMapper.convertToDTO(accountRepository.findById(id).get());
    }
}
