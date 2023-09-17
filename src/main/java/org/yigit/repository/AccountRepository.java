package org.yigit.repository;

import org.springframework.stereotype.Component;
import org.yigit.exception.BadRequestException;
import org.yigit.exception.RecordNotFoundException;
import org.yigit.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountRepository {
    public static List<Account> accountList= new ArrayList<>();
    public Account save(Account account){
        accountList.add(account);
        return account;
    };

    public List<Account> findAll() {
        return accountList;
    }

    public Account findById(UUID id) {
        return accountList.stream().filter(a->a.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new RecordNotFoundException("Account could not be found! "));
    }
}
