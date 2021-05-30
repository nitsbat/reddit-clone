package com.example.redditclone.mapper;

import com.example.redditclone.model.Post;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.dto.SubredditDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
// This will tell spring that it is mapstruct mapper and makes a bean of it in service using it
public interface SubredditMapper {

    @Mapping(target = "postCount", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDTO mapToSubredditDTO(Subreddit subreddit);

    default Integer mapPosts(List<Post> posts) {
        return posts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapToSubreddit(SubredditDTO subredditDTO);
}
