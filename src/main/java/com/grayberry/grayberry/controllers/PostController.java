package com.grayberry.grayberry.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grayberry.grayberry.models.Post;
import com.grayberry.grayberry.services.PostService;

@RestController
public class PostController
{
    @Autowired
    private PostService postService;
    
    @PostMapping("/Post/createNewPost")
    public ResponseEntity<String> createNewPost(@RequestBody Post post, HttpServletRequest request)
    {
        String sessionToken = request.getHeader("Authorization").split(" ")[1];
        String message = "{\"message\": " + "\"" + postService.createNewPost(sessionToken, post) + "\"}";
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(message);
    }
    
    @PostMapping("/Post/getPostsByPage")
    public ResponseEntity<String> getPostsByPage(@RequestBody String data)
    {
        String message = postService.getPostsByPage(data);
        
        if (message.equals("error"))
        {
            message = "{\"message\": \"An error has occured.\"}";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(message);
        }
        
        else if (message.equals("invalid"))
        {
            message = "{\"message\": \"Request is invalid.\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(message);
        }
        
        else
        {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(message);
        }
    }
    
    @PostMapping("/Post/getPostById")
    public ResponseEntity<String> getPostById(@RequestBody Post post)
    {
        Integer postId = post.getPostId();
        String postBody = postService.getPostById(postId);
        
        if (postBody == null)
        {
            String message = "{\"message\": \"Post not found.\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(message);
        }
        
        else
        {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(postBody);
        }
       
    }
    
    @PostMapping("/Post/deletePost")
    public ResponseEntity<String> deletePost(@RequestBody Post post, HttpServletRequest request)
    {
        String sessionToken = request.getHeader("Authorization").split(" ")[1];
        boolean successful = postService.deletePost(sessionToken, post);
        String message = "";
        
        if (successful)
        {
            message = "{\"message\": \"Post deleted.\"}";
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(message);
        }
        
        else
        {
            message = "{\"message\": \"Post not found or the post does not belong to the user.\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(message);
        }
    }
}
