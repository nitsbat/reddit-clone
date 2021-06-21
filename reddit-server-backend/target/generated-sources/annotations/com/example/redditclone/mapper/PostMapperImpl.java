package com.example.redditclone.mapper;

import com.example.redditclone.dto.PostRequest;
import com.example.redditclone.dto.PostResponse;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.model.User;
import java.util.Date;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-06T23:25:18+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.7 (JetBrains s.r.o.)"
)
@Component
public class PostMapperImpl extends PostMapper {

    @Override
    public PostResponse mapPostToResponse(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse postResponse = new PostResponse();

        postResponse.setPostName( post.getPostName() );
        postResponse.setDescription( post.getDescription() );
        postResponse.setId( post.getPostId() );
        postResponse.setUserName( postUserUsername( post ) );
        postResponse.setUrl( post.getUrl() );
        postResponse.setSubredditName( postSubredditName( post ) );
        if ( post.getVoteCount() != null ) {
            postResponse.setVoteCount( post.getVoteCount() );
        }

        postResponse.setDuration( getDuration(post) );
        postResponse.setCommentCount( getCommentsNumber(post) );

        return postResponse;
    }

    @Override
    public Post mapToPostFromRequest(PostRequest request, Subreddit subreddit, Date date, User currentUser) {
        if ( request == null && subreddit == null && date == null && currentUser == null ) {
            return null;
        }

        Post post = new Post();

        if ( request != null ) {
            post.setPostName( request.getPostName() );
            post.setDescription( request.getDescription() );
            post.setPostId( request.getPostId() );
            post.setUrl( request.getUrl() );
        }
        if ( subreddit != null ) {
            post.setSubreddit( subreddit );
        }
        if ( date != null ) {
            post.setCreatedDate( date );
        }
        if ( currentUser != null ) {
            post.setUser( currentUser );
        }
        post.setVoteCount( 0 );

        return post;
    }

    private String postUserUsername(Post post) {
        if ( post == null ) {
            return null;
        }
        User user = post.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private String postSubredditName(Post post) {
        if ( post == null ) {
            return null;
        }
        Subreddit subreddit = post.getSubreddit();
        if ( subreddit == null ) {
            return null;
        }
        String name = subreddit.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
