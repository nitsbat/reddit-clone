package com.example.redditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDTO {
    private Long id;
    private Long postId;
    private Date createdDate;
    private String text;
    private String userName;
}
