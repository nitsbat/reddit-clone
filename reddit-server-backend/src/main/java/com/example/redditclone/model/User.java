package com.example.redditclone.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotBlank(message = "Username cannot be blank.")
    private String userName;

    @NotBlank(message = "Password is required")
    private String password;

    @Email
    @NotEmpty(message = "Email-Id is required")
    private String emailId;

    @Temporal(TemporalType.DATE)
    private Date createdTime;

}
