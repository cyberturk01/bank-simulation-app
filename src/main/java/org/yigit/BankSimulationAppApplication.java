package org.yigit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.yigit.dto.AccountDTO;
import org.yigit.enums.AccountType;
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

        AccountDTO sender = accountService.createNewAccount(BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 1L);
        AccountDTO receiver = accountService.createNewAccount(BigDecimal.valueOf(50), new Date(), AccountType.CHECKING, 2L);
        AccountDTO receiver1 = accountService.createNewAccount(BigDecimal.valueOf(150), new Date(), AccountType.SAVING, 212L);
        AccountDTO receiver2 = accountService.createNewAccount(BigDecimal.valueOf(350), new Date(), AccountType.CHECKING, 122L);

//        accountService.listAllAccount().forEach(System.out::println);
//
//        //make transfer here
        transactionService.makeTransfer(sender,receiver,BigDecimal.valueOf(40),new Date(),"Transaction1");
//
        transactionService.findAllTransaction().forEach(System.out::println);

        accountService.listAllAccount().forEach(System.out::println);

    }

}
