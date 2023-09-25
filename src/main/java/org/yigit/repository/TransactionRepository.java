package org.yigit.repository;

import org.springframework.stereotype.Component;
import org.yigit.model.Transaction;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionRepository {

    public static List<Transaction> transactionList= new ArrayList<>();

    public Transaction save(Transaction transaction){
        transactionList.add(transaction);
        return transaction;
    };

    public List<Transaction> findAll() {
        return transactionList;
    }
}
