package org.yigit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.yigit.dto.TransactionDTO;
import org.yigit.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM transactions order by create_date desc limit 10")
    List<Transaction> findLast10Transactions();

    @Query(value = "SELECT t FROM Transaction t where t.sender.id = ?1 or t.receiver.id = ?1")
    List<Transaction> findTransactionListByAccountId(Long id);
}
