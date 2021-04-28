package com.example.redditclone.repository;

import com.example.redditclone.model.Post;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {

    Subreddit findByName(String name);

    List<Subreddit> findAllByUser(User user);

}
