package org.yigit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.yigit.dto.TransactionDTO;
import org.yigit.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

//    public static List<TransactionDTO> transactionDTOList = new ArrayList<>();
//
//    public TransactionDTO save(TransactionDTO transactionDTO){
//        transactionDTOList.add(transactionDTO);
//        return transactionDTO;
//    };
//
//    public List<TransactionDTO> findAll() {
//        return transactionDTOList;
//    }
}
