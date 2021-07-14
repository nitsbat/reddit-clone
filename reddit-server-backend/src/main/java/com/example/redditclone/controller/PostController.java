package com.example.redditclone.controller;

import com.example.redditclone.dto.PostRequest;
import com.example.redditclone.dto.PostResponse;
import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;

@RestController
@RequestMapping("/api/posts/")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity save(@RequestBody PostRequest request) throws SpringRedditException {
        postService.savePost(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public List<PostResponse> getPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) throws SpringRedditException {
        return new ResponseEntity<>(HttpStatus.OK).ok().body(postService.getPost(id));
    }

    @GetMapping("/subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostByReddit(@PathVariable Long id) throws SpringRedditException {
        return status(HttpStatus.OK).body(postService.getPostBySubreddit(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<PostResponse>> getPostByUsername(@PathVariable String username) throws SpringRedditException {
        return status(HttpStatus.OK).body(postService.getPostByUsername(username));
    }
}
