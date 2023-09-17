package org.yigit.service;

import org.yigit.model.Account;
import org.yigit.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TransactionService {
    Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message);

    List<Transaction> findAllTransaction();
}
