package com.example.redditclone.service;


import com.example.redditclone.builder.SubredditBuilder;
import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.mapper.SubredditMapper;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.model.SubredditDTO;
import com.example.redditclone.repository.SubredditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubredditService {

    @Autowired
    private SubredditRepository subredditRepository;

    @Autowired
    private SubredditMapper subredditMapper;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public List<SubredditDTO> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapToSubredditDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditDTO getSubRedit(Long id) throws SpringRedditException {
        Subreddit subreddit = subredditRepository
                .findById(id)
                .orElseThrow(() -> new SpringRedditException("No subreddit found with given id :" + id));
        return subredditMapper.mapToSubredditDTO(subreddit);
    }


    public SubredditDTO save(SubredditDTO subredditDTO) throws SpringRedditException {
        subredditRepository.save(subredditMapper.mapToSubreddit(subredditDTO));
        return subredditDTO;
    }
}
