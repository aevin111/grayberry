package com.grayberry.grayberry.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grayberry.grayberry.models.Account;
import com.grayberry.grayberry.services.AccountService;

@RestController
public class AccountController
{
    @Autowired
    private AccountService accountService;
    
    @PostMapping("/Account/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody String data, HttpServletRequest request)
    {
        String sessionToken = request.getHeader("Authorization").split(" ")[1];
        boolean successful = accountService.changePassword(data, sessionToken);
        String message = "";
        
        if (successful)
        {
            message = "Successfully changed password. Login again using your new password.";
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(message);
        }
        
        else
        {
            message = "You have either entered the wrong password, used a different token, and/or the token is invalid.";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(message);
        }
    }
    
    @PostMapping("/Account/register")
    public ResponseEntity<String> register(@RequestBody Account account)
    {
        String message = "";
        boolean successful = accountService.register(account);
        
        if (successful)
        {
            message = "{\"message\": \"Account successfully created.\"}";
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(message);
        }
        
        else
        {
            message = "{\"message\": \"Failed to create account.\"}";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }
    
    @PostMapping("/Account/login")
    public ResponseEntity<String> login(@RequestBody Account account)
    {
        String sessionToken = accountService.login(account);
        String message = "";
        
        if (sessionToken.equals("incorrect"))
        {
            message = "{\"message\": \"Your username or password is incorrect.\"}";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.APPLICATION_JSON).body(message);
        }
        
        else if (sessionToken.equals("banned"))
        {
            message = "{\"message\": \"You have been banned from this service.\"}";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(message);
        }
        
        else
        {
            message = "{\"sessionToken\": " + "\"" + sessionToken + "\"}";
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(message);
        }
    }
    
    @PostMapping("/Account/logout")
    public ResponseEntity<String> logout(HttpServletRequest request)
    {
        String sessionToken = request.getHeader("Authorization").split(" ")[1];
        boolean successful = accountService.logout(sessionToken);
        String message = "";
       
        if (successful)
        {
            message = "{\"message\": \"Successfully logged out.\"}";
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(message);
        }
        
        else
        {
            message = "{\"message\": \"Token is either invalid or has expired.\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(message);
        }
    }
}
