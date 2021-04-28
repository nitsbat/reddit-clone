package com.example.redditclone.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;

    @Lob
    @NotEmpty(message = "Description is required")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "postId")
    private Post post;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
