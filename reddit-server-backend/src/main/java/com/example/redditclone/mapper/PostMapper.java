package com.example.redditclone.mapper;

import com.example.redditclone.dto.PostRequest;
import com.example.redditclone.dto.PostResponse;
import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.model.*;
import com.example.redditclone.repository.CommentRepository;
import com.example.redditclone.repository.VoteRepository;
import com.example.redditclone.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

import static com.example.redditclone.model.VoteType.DOWNVOTE;
import static com.example.redditclone.model.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;


    @Mapping(target = "id", source = "postId")
    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(getCommentsNumber(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapPostToResponse(Post post);

    Integer getCommentsNumber(Post post) {
        return commentRepository.findAllByPost(post).size();
    }

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().getTime());
    }

    @Mapping(target = "postId", source = "request.postId")
    @Mapping(target = "postName", source = "request.postName")
    @Mapping(target = "description", source = "request.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "createdDate", source = "date")
    @Mapping(target = "user", source = "currentUser")
    @Mapping(target = "voteCount", constant = "0")
    public abstract Post mapToPostFromRequest(PostRequest request, Subreddit subreddit, Date date, User currentUser);

    boolean isPostUpVoted(Post post) throws SpringRedditException {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) throws SpringRedditException {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) throws SpringRedditException {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUser(post,
                    authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
