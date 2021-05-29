package com.example.redditclone.controller;

import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.model.SubredditDTO;
import com.example.redditclone.service.SubredditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
public class SubredditController {

    @Autowired
    private SubredditService subredditService;

    @GetMapping("/hello")
    public String hello()
            throws SpringRedditException {
        return "<h1>Hello World </h1";
    }

    @GetMapping
    public List<SubredditDTO> getAllSubRedits() {
        return subredditService.getAll();
    }

    @GetMapping("/{id}")
    public SubredditDTO getSubRedit(@PathVariable Long id) throws SpringRedditException {
        return subredditService.getSubRedit(id);
    }

    @PostMapping
    public SubredditDTO create(@RequestBody SubredditDTO subreddit) throws SpringRedditException {
        return subredditService.save(subreddit);
    }

}
