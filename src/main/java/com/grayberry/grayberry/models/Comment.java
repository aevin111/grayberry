package com.grayberry.grayberry.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "comment")
public class Comment
{
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    @Column(name = "post_id")
    private Integer postId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "comment_text", columnDefinition = "TEXT")
    private String commentText;
    
    public Comment(Integer commentId, Integer postId, Integer userId, String commentText)
    {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.commentText = commentText;
    }
    
    public Comment(Integer userId, Integer postId, String commentText)
    {
        this.postId = postId;
        this.userId = userId;
        this.commentText = commentText;
    }
    
    public Comment(Integer postId, String commentText)
    {
        this.postId = postId;
        this.userId = null;
        this.commentText = commentText;
    }
    
    public Comment()
    {
        
    }
    
    public Integer getCommentId()
    {
        return this.commentId;
    }
    
    public Integer getPostId()
    {
        return this.postId;
    }
    
    public Integer getUserId()
    {
        return this.userId;
    }
    
    public String getCommentText()
    {
        return this.commentText;
    }
    
    public void setCommentId(Integer commentId)
    {
        this.commentId = commentId;
    }
    
    public void setPostId(Integer postId)
    {
        this.postId = postId;
    }
    
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    
    public void setCommentText(String commentText)
    {
        this.commentText = commentText;
    }
}
