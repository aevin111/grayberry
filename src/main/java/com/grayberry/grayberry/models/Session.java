package com.grayberry.grayberry.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "session")
public class Session
{
    @Id
    @Column(name = "session_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sessionId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "session_token", columnDefinition = "TEXT")
    private String sessionToken;
    
    public Session(Integer sessionId, Integer userId, String sessionToken)
    {
        this.sessionId = sessionId;
        this.userId = userId;
        this.sessionToken = sessionToken;
    }
    
    public Session(Integer userId, String sessionToken)
    {
        this.userId = userId;
        this.sessionToken = sessionToken;
    }
    
    public Session()
    {
        
    }
    
    public Integer getSessionId()
    {
        return this.sessionId;
    }
    
    public Integer getUserId()
    {
        return this.userId;
    }
    
    public String getSessionToken()
    {
        return this.sessionToken;
    }
    
    public void setSessionId(Integer sessionId)
    {
        this.sessionId = sessionId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    
    public void setSessionToken(String sessionToken)
    {
        this.sessionToken = sessionToken;
    }
}