package com.example.redditclone;


import com.example.redditclone.model.Subreddit;
import com.example.redditclone.model.SubredditDTO;
import com.example.redditclone.model.User;

import java.util.Date;

public class SubredditBuilder {

    public static SubredditDTO buildToSubreddit(Subreddit subreddit) {
        SubredditDTO subredditDTO = new SubredditDTO();
        subredditDTO.setId(subreddit.getId());
        subredditDTO.setDescription(subreddit.getDescription());
        subredditDTO.setName(subreddit.getName());
        subredditDTO.setPostCount(subreddit.getPosts().size());
        return subredditDTO;
    }

    public static Subreddit buildFromSubreddit(SubredditDTO subredditDTO, User user) {

        Subreddit subreddit = new Subreddit();
        Date now = new Date(System.currentTimeMillis());
        subreddit.setCreatedBy(now);
        subreddit.setDescription(subreddit.getDescription());
        subreddit.setName(subredditDTO.getName());
        subreddit.setUser(user);
        return subreddit;
    }
}
