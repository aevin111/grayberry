package com.grayberry.grayberry.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.grayberry.grayberry.models.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>
{
    @Query("SELECT a FROM account a WHERE a.email = ?1")
    public Account getAccountByEmail(String email);
    @Query("SELECT a FROM account a WHERE a.userId = ?1")
    public Account getAccountByUserId(Integer userId);
    @Transactional
    @Modifying
    @Query(value = "UPDATE account SET password = ?1 WHERE user_id = ?2", nativeQuery = true)
    public void updateAccountPassword(String password, Integer userId);
}
