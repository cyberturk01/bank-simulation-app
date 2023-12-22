package org.yigit.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yigit.dto.AccountDTO;
import org.yigit.dto.TransactionDTO;
import org.yigit.enums.AccountType;
import org.yigit.exception.AccountOwnerShipException;
import org.yigit.exception.BadRequestException;
import org.yigit.exception.BalanceNotSufficientException;
import org.yigit.exception.UnderConstructionException;
import org.yigit.repository.AccountRepository;
import org.yigit.repository.TransactionRepository;
import org.yigit.service.TransactionService;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
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
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {
        if(!underConstruction){
            validateAccount(sender, receiver);
            checkAccountOwnerShip(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);
            //After all validations are done, and money is transferred, we need to create Transaction
            TransactionDTO transactionDTO = TransactionDTO.builder()
                    .sender(sender.getId())
                    .receiver(receiver.getId())
                    .amount(amount)
                    .createDate(creationDate)
                    .message(message).build();
            //Save it into DB and return it.
            return transactionRepository.save(transactionDTO);
        }else{
            throw new UnderConstructionException("App is under construction, please try again later");
        }


    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if(checkSenderBalance(sender,amount)){
            //update sender and receiver balance
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));
        }else{
            throw new BalanceNotSufficientException("Balance is not enough for this transfer.");
        }
    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {
        //verify sender has enough balance to send
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void checkAccountOwnerShip(AccountDTO sender, AccountDTO receiver) {
        if (sender.getAccountType().equals(AccountType.SAVING) ||
                receiver.getAccountType().equals(AccountType.SAVING) &&
                        !sender.getUserId().equals(receiver.getUserId())) {

            throw new AccountOwnerShipException("If one of the account is saving, user must be the same or sender and receiver");
        }
    }

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
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
    public List<TransactionDTO> findAllTransaction() {
        return transactionRepository.findAll();
    }

    @Override
    public List<TransactionDTO> last10Transaction() {
        return transactionRepository.findAll().stream()
                .sorted(Comparator.comparing(TransactionDTO::getCreateDate).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListById(UUID id) {
        return transactionRepository.findAll().stream()
                .filter(transactionDTO -> transactionDTO.getSender().equals(id) || transactionDTO.getReceiver().equals(id) )
                .collect(Collectors.toList());
    }
}
