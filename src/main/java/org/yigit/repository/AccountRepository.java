package org.yigit.repository;

import org.springframework.stereotype.Component;
import org.yigit.dto.AccountDTO;
import org.yigit.exception.RecordNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class AccountRepository {
    public static List<AccountDTO> accountDTOList = new ArrayList<>();
    public AccountDTO save(AccountDTO accountDTO){
        accountDTOList.add(accountDTO);
        return accountDTO;
    };

    public List<AccountDTO> findAll() {
        return accountDTOList;
    }

    public AccountDTO findById(UUID id) {
        return accountDTOList.stream().filter(a->a.getId().equals(id))
                .findFirst()
                .orElseThrow(()->new RecordNotFoundException("Account could not be found! "));
    }
    public void deleteById(UUID id) {
        accountDTOList.removeIf(account -> account.getId().equals(id));
        System.out.println(accountDTOList);
    }

}
