package com.grayberry.grayberry.services;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.grayberry.grayberry.models.Account;
import com.grayberry.grayberry.models.Role;
import com.grayberry.grayberry.models.Session;
import com.grayberry.grayberry.repositories.AccountRepository;
import com.grayberry.grayberry.repositories.SessionRepository;
import com.grayberry.grayberry.security.PasswordVerifier;
import com.grayberry.grayberry.security.TokenGenerator;

@Service
public class AccountService
{
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SessionRepository sessionRepository;
    
    public boolean changePassword(String data, String sessionToken)
    {
        ObjectMapper mapper = new ObjectMapper();
        Logger logger = LoggerFactory.getLogger(this.getClass());
        String oldPassword = "";
        String newPassword = "";
        
        try
        {
            Map<String, String> dataAsMap = mapper.readValue(data, Map.class);
            oldPassword = dataAsMap.get("oldPassword");
            newPassword = dataAsMap.get("newPassword");
        }
        
        catch (IOException e)
        {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        
        Session session = sessionRepository.getSessionInfoFromToken(sessionToken);
        
        if (session == null)
        {
            return false;
        }
        
        else
        {
            Account account = accountRepository.getAccountByUserId(session.getUserId());
            boolean correct = new PasswordVerifier().verifyPassword(oldPassword, account.getPassword());
            
            if (correct)
            {
                sessionRepository.destroyAllSessions(account.getUserId());
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12, new SecureRandom());
                newPassword = encoder.encode(newPassword);
                accountRepository.updateAccountPassword(newPassword, account.getUserId());
                return true;
            }
            
            else
            {
                return false;
            }
        }
    }
    
    public boolean register(Account account)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12, new SecureRandom());
        account.setPassword(encoder.encode(account.getPassword()));
        account.setRoleId(Role.PENDING);
        
        if (accountRepository.save(account) != null)
        {
            return true;
        }
        
        else
        {
            return false;
        }
    }
    
    public String login(Account account)
    {
        Account existing = accountRepository.getAccountByEmail(account.getEmail());
        boolean correct = new PasswordVerifier().verifyPassword(account.getPassword(), existing.getPassword());
        
        if (correct)
        {
            if (existing.getRoleId() != Role.BANNED)
            {
                String sessionToken = new TokenGenerator().generateToken();
                sessionRepository.createSession(existing.getUserId(), sessionToken);
                return sessionToken;
            }
            
            else
            {
                return "banned";
            }
        }
        
        else
        {
            return "incorrect";
        }
    }
    
    public boolean logout(String sessionToken)
    {
        int rowsReturned = sessionRepository.destroySession(sessionToken);
        
        if (rowsReturned == 0)
        {
            return false;
        }
        
        else
        {
            return true;
        }
    }
}