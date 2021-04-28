package com.example.redditclone.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long voteId;

    private VoteType voteType;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @NotEmpty
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    private Post post;

    public Long getVoteId() {
        return voteId;
    }

    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }

    public VoteType getVoteType() {
        return voteType;
    }

    public void setVoteType(VoteType voteType) {
        this.voteType = voteType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
