package org.yigit.service.impl;

import org.apache.logging.log4j.util.PropertySource;
import org.springframework.stereotype.Component;
import org.yigit.enums.AccountType;
import org.yigit.exception.AccountOwnerShipException;
import org.yigit.exception.BadRequestException;
import org.yigit.model.Account;
import org.yigit.model.Transaction;
import org.yigit.repository.AccountRepository;
import org.yigit.service.TransactionService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;

    public TransactionServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        validateAccount(sender, receiver);
        checkAccountOwnerShip(sender, receiver);
        return null;
    }

    private void checkAccountOwnerShip(Account sender, Account receiver) {
        if (sender.getAccountType().equals(AccountType.SAVING) ||
                receiver.getAccountType().equals(AccountType.SAVING) &&
                        !sender.getUserId().equals(receiver.getUserId())) {

            throw new AccountOwnerShipException("If one of the account is saving, user must be the same or sender and receiver");
        }
    }

    private void validateAccount(Account sender, Account receiver) {
        /*
         * if any of the account is null
         * if any ids are the same (same account)
         * if any account exist in the database (repository)
         * */
        if (sender == null || receiver == null) {
            throw new BadRequestException("Sender or Receiver cannot be null");
        }
        if (sender.getId().equals(receiver.getId())) {
            throw new BadRequestException("Sender account needs to be different than receiver account");
        }
        findAccountById(sender.getId());
        findAccountById(receiver.getId());
    }

    private void findAccountById(UUID id) {
        accountRepository.findById(id);
    }

    @Override
    public List<Transaction> findAllTransaction() {
        return null;
    }
}
