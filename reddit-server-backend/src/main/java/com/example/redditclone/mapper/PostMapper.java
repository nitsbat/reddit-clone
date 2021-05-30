package com.example.redditclone.mapper;

import com.example.redditclone.dto.PostRequest;
import com.example.redditclone.dto.PostResponse;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapPostToResponse(Post post);

    @Mapping(target = "postId", source = "request.postId")
    @Mapping(target = "postName", source = "request.postName")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "subreddit",source = "subreddit")
    @Mapping(target = "createdDate", source = "date")
    @Mapping(target = "user", source = "currentUser")
    Post mapToPostFromRequest(PostRequest request, Subreddit subreddit, Date date, User currentUser);
}
