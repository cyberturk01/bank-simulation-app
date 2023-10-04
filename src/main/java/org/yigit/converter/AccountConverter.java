package org.yigit.converter;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.yigit.model.Account;
import org.yigit.model.Transaction;
import org.yigit.service.AccountService;

import java.util.UUID;

@Component
@ConfigurationPropertiesBinding
public class AccountConverter implements Converter<String, Account> {

    private final AccountService accountService;

    public AccountConverter(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Account convert(String source) {
        return accountService.findById(UUID.fromString(source));
    }
}
