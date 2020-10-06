package com.atomiclab.socialgamerbackend.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/play")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/post")
    public boolean makePost(@RequestBody Post post, @RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return postService.uploadPost(post, token);
    }
    @GetMapping("/feed")
    public List<Post> getFeed(@RequestHeader("X-Firebase-Auth") String token)
            throws InterruptedException, ExecutionException {
        return postService.getFeed(token);
    }
}
