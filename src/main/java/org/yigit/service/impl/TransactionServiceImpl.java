package org.yigit.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.yigit.enums.AccountType;
import org.yigit.exception.AccountOwnerShipException;
import org.yigit.exception.BadRequestException;
import org.yigit.exception.BalanceNotSufficientException;
import org.yigit.exception.UnderConstructionException;
import org.yigit.model.Account;
import org.yigit.model.Transaction;
import org.yigit.repository.AccountRepository;
import org.yigit.repository.TransactionRepository;
import org.yigit.service.TransactionService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        if(!underConstruction){
            validateAccount(sender, receiver);
            checkAccountOwnerShip(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);
            //After all validations are done, and money is transferred, we need to create Transaction
            Transaction transaction= Transaction.builder()
                    .sender(sender.getId())
                    .receiver(receiver.getId())
                    .amount(amount)
                    .createDate(creationDate)
                    .message(message).build();
            //Save it into DB and return it.
            return transactionRepository.save(transaction);
        }else{
            throw new UnderConstructionException("App is under construction, please try again later");
        }


    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {
        if(checkSenderBalance(sender,amount)){
            //update sender and receiver balance
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }else{
            throw new BalanceNotSufficientException("Balance is not enough for this transfer.");
        }
    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        //verify sender has enough balance to send
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
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
        return transactionRepository.findAll();
    }
}
