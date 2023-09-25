package org.yigit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.yigit.enums.AccountType;
import org.yigit.model.Account;
import org.yigit.service.AccountService;
import org.yigit.service.TransactionService;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BankSimulationAppApplication.class, args);
        AccountService accountService = context.getBean(AccountService.class);
        TransactionService transactionService = context.getBean(TransactionService.class);

        Account sender = accountService.createNewAccount(BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 1L);
        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(50), new Date(), AccountType.CHECKING, 2L);

        accountService.listAllAccount().forEach(System.out::println);

        //make transfer here
        transactionService.makeTransfer(sender,receiver,BigDecimal.valueOf(40),new Date(),"Transaction1");

        transactionService.findAllTransaction().forEach(System.out::println);

        accountService.listAllAccount().forEach(System.out::println);

    }

}
