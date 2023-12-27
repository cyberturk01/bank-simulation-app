package org.yigit.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.yigit.dto.AccountDTO;
import org.yigit.service.AccountService;

@Component
public class AccountConverter implements Converter<String, AccountDTO> {

    private final AccountService accountService;

    public AccountConverter(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountDTO convert(String source) {
        if(source==null || source.equals("")){
            return null;
        }
        return accountService.findById(Long.parseLong(source));
    }
}
