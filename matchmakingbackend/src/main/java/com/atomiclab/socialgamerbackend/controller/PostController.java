package com.atomiclab.socialgamerbackend.controller;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Comment;
import com.atomiclab.socialgamerbackend.domain.model.Post;
import com.atomiclab.socialgamerbackend.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("/posts/{id:.+}")
    public List<Post> getPosts(@RequestHeader("X-Firebase-Auth") String token,@PathVariable String id)
            throws InterruptedException, ExecutionException {
        return postService.getPosts(id);
    }
    @GetMapping("/comments/{idPublicacion:.+}")
    public List<Comment> getComments(@RequestHeader("X-Firebase-Auth") String token, @PathVariable String idPublicacion)
            throws InterruptedException, ExecutionException {
        return postService.getComments(idPublicacion, token);
    }
    @PostMapping("/comments")
    public boolean makeComment(@RequestHeader("X-Firebase-Auth") String token, @RequestBody Comment comment)
            throws InterruptedException, ExecutionException {
        return postService.makeComment(token, comment);
    }

    @GetMapping("/likes/{idPublicacion:.+}")
    public Integer likes(@PathVariable String idPublicacion)
            throws InterruptedException, ExecutionException {
        return postService.getLikes(idPublicacion);
    }

    @PostMapping("/like/{idPublicacion:.+}")
    public boolean like(@RequestHeader("X-Firebase-Auth") String token, @PathVariable String idPublicacion)
            throws InterruptedException, ExecutionException {
        return postService.like(idPublicacion, token);
    }
}
