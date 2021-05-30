package com.example.redditclone.service;

import com.example.redditclone.dto.PostRequest;
import com.example.redditclone.dto.PostResponse;
import com.example.redditclone.exception.SpringRedditException;
import com.example.redditclone.exception.SubredditException;
import com.example.redditclone.mapper.PostMapper;
import com.example.redditclone.model.Post;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.model.User;
import com.example.redditclone.repository.PostRepository;
import com.example.redditclone.repository.SubredditRepository;
import com.example.redditclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SubredditRepository subredditRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::mapPostToResponse)
                .collect(Collectors.toList());
    }


    public PostResponse getPost(Long id) throws SpringRedditException {
        return postMapper.mapPostToResponse(postRepository.findById(id).orElseThrow(
                () -> new SpringRedditException("Post not found for id : " + id)
        ));
    }

    public List<PostResponse> getPostBySubreddit(Long id) throws SpringRedditException {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(
                () -> new SubredditException("No subreddit found for id : " + id)
        );
        return postRepository.findAllBySubreddit(subreddit).stream()
                .map(postMapper::mapPostToResponse).collect(Collectors.toList());
    }

    public List<PostResponse> getPostByUsername(String username) throws SpringRedditException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new SpringRedditException("User not found with username : " + username)
        );
        return postRepository.findAllByUser(user).stream()
                .map(postMapper::mapPostToResponse).collect(Collectors.toList());
    }

    public void savePost(PostRequest request) throws SpringRedditException {
        Date date = new Date(System.currentTimeMillis());
        User currentUser = authService.getCurrentUser();
        Subreddit subreddit = subredditRepository.findByName(request.getSubredditName());

        postRepository.save(postMapper.mapToPostFromRequest(request,subreddit,date,currentUser));
    }
}
