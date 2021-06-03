package com.example.redditclone.repository;

import com.example.redditclone.model.BlacklistTokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlackListTokenRepository extends JpaRepository<BlacklistTokens, Long> {
    Optional<BlacklistTokens> findByJwtToken(String jwtToken);
}
