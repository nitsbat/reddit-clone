package com.example.redditclone.controller;

import com.example.redditclone.dto.CommentsDTO;
import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @PostMapping
    public ResponseEntity createPost(@RequestBody CommentsDTO commentsDTO) throws SpringRedditException {
        commentsService.save(commentsDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentsDTO>> getAllCommentsByPosts(@PathVariable Long postId) throws SpringRedditException {
        return ResponseEntity.ok(commentsService.getAllCommentsForPost(postId));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<CommentsDTO>> getAllCommentsByUser(@PathVariable String username) throws SpringRedditException {
        return ResponseEntity.ok(commentsService.getAllCommentsForUser(username));
    }
}
