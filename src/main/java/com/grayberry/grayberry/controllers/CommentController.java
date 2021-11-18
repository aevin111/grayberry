package com.grayberry.grayberry.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grayberry.grayberry.models.Comment;
import com.grayberry.grayberry.services.CommentService;

@RestController
public class CommentController
{
    @Autowired
    private CommentService commentService;
}
