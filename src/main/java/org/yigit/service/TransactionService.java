package org.yigit.service;

import org.yigit.dto.AccountDTO;
import org.yigit.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
    TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message);

    List<TransactionDTO> findAllTransaction();
    List<TransactionDTO> last10Transaction();
    List<TransactionDTO> findTransactionListById(UUID id);
}
