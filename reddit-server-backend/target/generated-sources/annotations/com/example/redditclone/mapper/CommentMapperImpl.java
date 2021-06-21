package com.example.redditclone.mapper;

import com.example.redditclone.dto.CommentsDTO;
import com.example.redditclone.model.Comment;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.User;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-06T23:25:18+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.7 (JetBrains s.r.o.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentsDTO mapToDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentsDTO commentsDTO = new CommentsDTO();

        commentsDTO.setId( comment.getCommentId() );
        commentsDTO.setText( comment.getDescription() );
        commentsDTO.setCreatedDate( comment.getCreatedDate() );

        commentsDTO.setPostId( comment.getPost().getPostId() );
        commentsDTO.setUserName( comment.getUser().getUsername() );

        return commentsDTO;
    }

    @Override
    public Comment mapToCommentDTO(CommentsDTO commentsDTO, User user, Post post) {
        if ( commentsDTO == null && user == null && post == null ) {
            return null;
        }

        Comment comment = new Comment();

        if ( commentsDTO != null ) {
            comment.setDescription( commentsDTO.getText() );
        }
        if ( user != null ) {
            comment.setUser( user );
        }
        if ( post != null ) {
            comment.setPost( post );
        }
        comment.setCreatedDate( getCurrentDate() );

        return comment;
    }
}
