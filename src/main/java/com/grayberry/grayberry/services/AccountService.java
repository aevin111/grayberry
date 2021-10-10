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
    
    public String changePassword(String data, String sessionToken)
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
            return "invalid";
        }
        
        Session session = sessionRepository.getSessionInfoFromToken(sessionToken);
        Account account = accountRepository.getAccountByUserId(session.getUserId());
        
        if (account.getPassword().equals(oldPassword))
        {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12, new SecureRandom());
            newPassword = encoder.encode(newPassword);
            accountRepository.updateAccountPassword(newPassword, account.getEmail());
            return "Successfully updated password.";
        }
        
        else
        {
            return "Wrong password.";
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
        
        if (correct == true)
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
    
    public String logout(String sessionToken)
    {
        int rowsReturned = sessionRepository.destroySession(sessionToken);
        
        if (rowsReturned == 0)
        {
            return "failed";
        }
        
        else
        {
            return "success";
        }
    }
}