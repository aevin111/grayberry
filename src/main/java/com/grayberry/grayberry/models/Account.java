package com.grayberry.grayberry.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "account")
public class Account
{
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password", columnDefinition = "TEXT")
    private String password;
    @Column(name = "role_id")
    private Integer roleId;
    
    public Account(Integer userId, String username, String email, String password, Integer roleId)
    {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }
    
    public Account(Integer userId, String username, String password, Integer roleId)
    {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
    }
    
    public Account(String username, String email, String password)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleId = 5;
    }
    
    public Account(String email, String password)
    {
        this.email = email;
        this.password = password;
    }
    
    public Account()
    {
        
    }
    
    public Integer getUserId()
    {
        return this.userId;
    }
    
    public String getUsername()
    {
        return this.username;
    }
    
    public String getEmail()
    {
        return this.email;
    }
    
    public String getPassword()
    {
        return this.password;
    }
    
    public Integer getRoleId()
    {
        return this.roleId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public void setRoleId(Integer roleId)
    {
        this.roleId = roleId;
    }
}