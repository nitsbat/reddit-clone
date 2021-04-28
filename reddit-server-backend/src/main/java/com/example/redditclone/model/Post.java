package com.example.redditclone.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long postId;

    @NotBlank(message = "postname is required")
    private String postName;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;


    private Integer voteCount;

    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "subreddit_id")
    private Subreddit subreddit;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Subreddit getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(Subreddit subreddit) {
        this.subreddit = subreddit;
    }
}
