package com.example.redditclone.service;

import com.example.redditclone.builder.MailContentBuilder;
import com.example.redditclone.dto.CommentsDTO;
import com.example.redditclone.exception.PostNotFoundException;
import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.mapper.CommentMapper;
import com.example.redditclone.model.Comment;
import com.example.redditclone.model.NotificationEmail;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.User;
import com.example.redditclone.repository.CommentRepository;
import com.example.redditclone.repository.PostRepository;
import com.example.redditclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentsService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private MailContentBuilder mailContentBuilder;

    @Autowired
    private MailService mailService;

    public List<CommentsDTO> getAllCommentsForPost(Long postId) throws SpringRedditException {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not found for id " + postId)
        );
        return commentRepository.findAllByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentsDTO> getAllCommentsForUser(String username) throws SpringRedditException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new SpringRedditException("User not found for username : " + username)
        );
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public void save(CommentsDTO commentsDTO) throws SpringRedditException {
        User user = authService.getCurrentUser();
        Post post = postRepository.findById(commentsDTO.getPostId()).orElseThrow(
                () -> new PostNotFoundException("Post not found for id " + commentsDTO.getPostId())
        );
        Comment comment = commentMapper.mapToCommentDTO(commentsDTO, user, post);
        commentRepository.save(comment);

        java.lang.String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + "");
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post",
                user.getEmailId(), message));
    }
}
