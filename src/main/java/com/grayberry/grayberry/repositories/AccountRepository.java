package com.grayberry.grayberry.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
