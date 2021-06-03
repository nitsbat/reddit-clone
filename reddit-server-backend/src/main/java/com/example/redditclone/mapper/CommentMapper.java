package com.example.redditclone.mapper;

import com.example.redditclone.dto.CommentsDTO;
import com.example.redditclone.model.Comment;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", source = "comment.commentId")
    @Mapping(target = "text", source = "comment.description")
    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentsDTO mapToDto(Comment comment);

    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "description", source = "commentsDTO.text")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "createdDate", expression = "java(getCurrentDate())")
    Comment mapToCommentDTO(CommentsDTO commentsDTO, User user, Post post);

    default Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
}
