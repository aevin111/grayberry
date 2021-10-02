package com.grayberry.grayberry.services;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityNotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.grayberry.grayberry.datetime.GrayberryDateTime;
import com.grayberry.grayberry.models.Post;
import com.grayberry.grayberry.repositories.PostRepository;
import com.grayberry.grayberry.repositories.SessionRepository;

@Service
public class PostService
{
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private SessionRepository sessionRepository;
    
    public String createNewPost(String sessionToken, Post post)
    {
        Integer userId = sessionRepository.getSessionInfoFromToken(sessionToken).getUserId();
        postRepository.createPost(userId, LocalDateTime.now(), post.getPostTitle(), post.getPostText(), post.getCommentsAllowed());
        return "Successfully created post";
    }
    
    public String getPostsByPage(String data)
    {
        ObjectMapper mapper = new ObjectMapper();
        Logger logger = LoggerFactory.getLogger(this.getClass());
        int page = -1;
        int numberOfPosts = -1;
        
        try
        {
            Map<String, Integer> dataAsMap = mapper.readValue(data, Map.class);
            page = dataAsMap.get("page") - 1;
            numberOfPosts = dataAsMap.get("numberOfPosts");
        }
        
        catch (IOException e)
        {
            logger.error(e.getLocalizedMessage());
            return "invalid";
        }
        
        if ((page <= -1 || numberOfPosts <= 0))
        {
            return "invalid";
        }
        
        Pageable pageable = PageRequest.of(page, numberOfPosts);
        Page<Post> posts = postRepository.findAll(pageable);
        Map<String, Post> mapOfPosts = new HashMap<>();
        
        for (Post post : posts.getContent())
        {
            mapOfPosts.put(Integer.toString(post.getPostId()), post);
        }
        
        String message = "";
        
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            message = objectMapper.writeValueAsString(mapOfPosts);
        }
        
        catch (JsonProcessingException e)
        {
            logger.error(e.getLocalizedMessage());
            return "error";
        }
        
        return message;
    }
    
    public String getPostById(Integer postId)
    {
        Map<String, Object> map = new HashMap<>();
        String message = "";
        Logger logger = LoggerFactory.getLogger(this.getClass());
        
        try
        {
            Post post = postRepository.getById(postId);
            map.put("postId", post.getPostId());
            map.put("userId", post.getUserId());
            map.put("dateTimePosted", new GrayberryDateTime().getDateTime(post.getDateTimePosted()));
            map.put("postTitle", post.getPostTitle());
            map.put("postText", post.getPostText());
            map.put("commentsAllowed", post.getCommentsAllowed());
            
            try 
            {
                message = new ObjectMapper().writeValueAsString(map);
                return message;
            }
            
            catch (JsonProcessingException e)
            {
                logger.error(e.getMessage());
                message = "empty";
                return message;
            }
        }
        
        catch (EntityNotFoundException e)
        {
            message = "empty";
            return message;
        }
    }
    
    public String deletePost(String sessionToken, Post post)
    {
        Integer userId = sessionRepository.getSessionInfoFromToken(sessionToken).getUserId();
        int rowsReturned  = postRepository.deletePost(post.getPostId(), userId);
        
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
