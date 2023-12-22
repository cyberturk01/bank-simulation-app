package org.yigit.repository;

import org.springframework.stereotype.Component;
import org.yigit.dto.TransactionDTO;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionRepository {

    public static List<TransactionDTO> transactionDTOList = new ArrayList<>();

    public TransactionDTO save(TransactionDTO transactionDTO){
        transactionDTOList.add(transactionDTO);
        return transactionDTO;
    };

    public List<TransactionDTO> findAll() {
        return transactionDTOList;
    }
}
