package com.example.redditclone.controller;

import com.example.redditclone.dto.VoteDto;
import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/votes/")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity createVote(@RequestBody VoteDto voteDTO) throws SpringRedditException {
        voteService.save(voteDTO);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
