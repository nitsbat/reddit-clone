package com.example.redditclone.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Community Name field is required")
    private String name;

    @Lob
    @NotBlank(message = "Description is required")
    private String description;

    @Temporal(TemporalType.DATE)
    private Date createdBy;

    @OneToMany
    private List<Post> posts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Date createdBy) {
        this.createdBy = createdBy;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
