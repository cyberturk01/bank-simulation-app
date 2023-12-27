package org.yigit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yigit.entity.Account;
import org.yigit.enums.AccountStatus;

import java.util.List;


@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    List<Account> findAllByAccountStatus(AccountStatus accountStatus);

}
