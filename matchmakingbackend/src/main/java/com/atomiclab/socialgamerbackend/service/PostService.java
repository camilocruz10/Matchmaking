package com.atomiclab.socialgamerbackend.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.atomiclab.socialgamerbackend.domain.model.Post;

import org.springframework.stereotype.Service;

@Service
public interface PostService {
    public boolean uploadPost(Post post, String token) throws InterruptedException, ExecutionException;
	public List<Post> getFeed(String token) throws InterruptedException, ExecutionException;
}
