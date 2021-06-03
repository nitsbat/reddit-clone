package com.example.redditclone.service;

import com.example.redditclone.dto.VoteDto;
import com.example.redditclone.exception.PostNotFoundException;
import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.exception.VoteAlreadyPresentException;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.Vote;
import com.example.redditclone.model.VoteType;
import com.example.redditclone.repository.PostRepository;
import com.example.redditclone.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AuthService authService;

    public void save(VoteDto voteDTO) throws SpringRedditException {
        Post post = postRepository.findById(voteDTO.getPostId()).orElseThrow(
                () -> new PostNotFoundException("Post not found for id : " + voteDTO.getPostId())
        );
        Optional<Vote> vote = voteRepository.findTopByPostAndUser(post, authService.getCurrentUser());
        if (vote.isPresent()) {
            if (vote.get().getVoteType() == voteDTO.getVoteType()) {
                throw new VoteAlreadyPresentException("You have already " + voteDTO.getVoteType() +
                        "d the post with post id : " + voteDTO.getPostId());
            }
        }

        if (VoteType.UPVOTE == voteDTO.getVoteType()) {
            post.setVoteCount(post.getVoteCount() + 1);
        }

        if (VoteType.DOWNVOTE == voteDTO.getVoteType()) {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(mapToVote(voteDTO, post));
        postRepository.save(post);
    }

    private Vote mapToVote(VoteDto voteDTO, Post post) throws SpringRedditException {
        Vote vote = new Vote();
        vote.setPost(post);
        vote.setUser(authService.getCurrentUser());
        vote.setVoteType(voteDTO.getVoteType());
        return vote;
    }
}
