package com.fundfun.fundfund.repository.vote;

import com.fundfun.fundfund.domain.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {
    Vote findByPostId(UUID postId);

}
