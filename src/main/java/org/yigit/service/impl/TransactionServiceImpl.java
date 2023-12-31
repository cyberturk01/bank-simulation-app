package org.yigit.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yigit.dto.AccountDTO;
import org.yigit.dto.TransactionDTO;
import org.yigit.entity.Transaction;
import org.yigit.enums.AccountType;
import org.yigit.exception.AccountOwnerShipException;
import org.yigit.exception.BadRequestException;
import org.yigit.exception.BalanceNotSufficientException;
import org.yigit.exception.UnderConstructionException;
import org.yigit.mapper.TransactionMapper;
import org.yigit.repository.AccountRepository;
import org.yigit.repository.TransactionRepository;
import org.yigit.service.AccountService;
import org.yigit.service.TransactionService;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Value("${under_construction}")
    private boolean underConstruction;

    private final AccountService accountService;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionServiceImpl(AccountService accountService, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.accountService = accountService;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public TransactionDTO makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) {
        if(!underConstruction){
            validateAccount(sender, receiver);
            checkAccountOwnerShip(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);
            //After all validations are done, and money is transferred, we need to create Transaction
            TransactionDTO transactionDTO = new TransactionDTO(sender,receiver,amount,message,creationDate);
            transactionRepository.save(transactionMapper.convertToEntity(transactionDTO));
            //Save it into DB and return it.
            return transactionDTO;
        }else{
            throw new UnderConstructionException("App is under construction, please try again later");
        }


    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) {
        if(checkSenderBalance(sender,amount)){
            //update sender and receiver balance
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

            AccountDTO senderId = accountService.findById(sender.getId());
            senderId.setBalance(sender.getBalance());
            accountService.updateAccount(senderId);

            AccountDTO receiverId = accountService.findById(receiver.getId());
            receiverId.setBalance(sender.getBalance());
            accountService.updateAccount(receiverId);
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

    private void findAccountById(Long id) {
        accountService.findById(id);
    }

    @Override
    public List<TransactionDTO> findAllTransaction() {
        List<Transaction> transactionsList = transactionRepository.findAll();
        return transactionsList.stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> last10Transaction() {
        List<Transaction> last10Transactions = transactionRepository.findLast10Transactions();
        return last10Transactions.stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
//        return transactionRepository.findAll().stream()
//                .sorted(Comparator.comparing(Transaction::getCreateDate).reversed())
//                .map(transactionMapper::convertToDTO)
//                .limit(10)
//                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListById(Long id) {
        List<Transaction> listByAccountId = transactionRepository.findTransactionListByAccountId(id);
        return listByAccountId.stream().map(transactionMapper::convertToDTO).collect(Collectors.toList());
//        return transactionRepository.findAll().stream()
//                .filter(transactionDTO -> transactionDTO.getSender().equals(id) || transactionDTO.getReceiver().equals(id) )
//                .collect(Collectors.toList());
    }
}
