package com.grayberry.grayberry.models;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "post")
public class Post
{
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "date_time_posted")
    private LocalDateTime dateTimePosted;
    @Column(name = "post_title")
    private String postTitle;
    @Column(name = "post_text", columnDefinition = "TEXT")
    private String postText;
    @Column(name = "comments_allowed")
    private Boolean commentsAllowed;
    
    public Post(Integer postId, Integer userId, LocalDateTime dateTimePosted, String postTitle, String postText, Boolean commentsAllowed)
    {
        this.postId = postId;
        this.userId = userId;
        this.dateTimePosted = dateTimePosted;
        this.postTitle = postTitle;
        this.postText = postText;
        this.commentsAllowed = commentsAllowed;
    }
    
    public Post(Integer userId, LocalDateTime dateTimePosted, String postTitle, String postText, Boolean commentsAllowed)
    {
        this.userId = userId;
        this.dateTimePosted = dateTimePosted;
        this.postTitle = postTitle;
        this.postText = postText;
        this.commentsAllowed = commentsAllowed;
    }
    
    public Post()
    {
        
    }
    
    public Integer getPostId()
    {
        return this.postId;
    }
    
    public Integer getUserId()
    {
        return this.userId;
    }
    
    public LocalDateTime getDateTimePosted()
    {
        return this.dateTimePosted;
    }
    
    public String getPostTitle()
    {
        return this.postTitle;
    }
    
    public String getPostText()
    {
        return this.postText;
    }
    
    public Boolean getCommentsAllowed()
    {
        return this.commentsAllowed;
    }
    
    public void setPostId(Integer postId)
    {
        this.postId = postId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    
    public void setDateTimePosted(LocalDateTime dateTimePosted)
    {
        this.dateTimePosted = dateTimePosted;
    }
    
    public void setPostTitle(String postTitle)
    {
        this.postTitle = postTitle;
    }
    
    public void setPostText(String postText)
    {
        this.postText = postText;
    }
    
    public void setCommentsAllowed(Boolean commentsAllowed)
    {
        this.commentsAllowed = commentsAllowed;
    }
}
