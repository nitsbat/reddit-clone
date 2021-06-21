package com.example.redditclone.mapper;

import com.example.redditclone.dto.SubredditDTO;
import com.example.redditclone.model.Subreddit;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-06T23:25:18+0530",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.7 (JetBrains s.r.o.)"
)
@Component
public class SubredditMapperImpl implements SubredditMapper {

    @Override
    public SubredditDTO mapToSubredditDTO(Subreddit subreddit) {
        if ( subreddit == null ) {
            return null;
        }

        SubredditDTO subredditDTO = new SubredditDTO();

        subredditDTO.setId( subreddit.getId() );
        subredditDTO.setName( subreddit.getName() );
        subredditDTO.setDescription( subreddit.getDescription() );

        subredditDTO.setPostCount( mapPosts(subreddit.getPosts()) );

        return subredditDTO;
    }

    @Override
    public Subreddit mapToSubreddit(SubredditDTO subredditDTO) {
        if ( subredditDTO == null ) {
            return null;
        }

        Subreddit subreddit = new Subreddit();

        subreddit.setId( subredditDTO.getId() );
        subreddit.setName( subredditDTO.getName() );
        subreddit.setDescription( subredditDTO.getDescription() );

        return subreddit;
    }
}
