package org.yigit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yigit.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
//    public static List<AccountDTO> accountDTOList = new ArrayList<>();
//    public AccountDTO save(AccountDTO accountDTO){
//        accountDTOList.add(accountDTO);
//        return accountDTO;
//    };
//
//    public List<AccountDTO> findAll() {
//        return accountDTOList;
//    }
//
//    public AccountDTO findById(Long id) {
//        return accountDTOList.stream().filter(a->a.getId().equals(id))
//                .findFirst()
//                .orElseThrow(()->new RecordNotFoundException("Account could not be found! "));
//    }
//    public void deleteById(UUID id) {
//        accountDTOList.removeIf(account -> account.getId().equals(id));
//        System.out.println(accountDTOList);
//    }

}
