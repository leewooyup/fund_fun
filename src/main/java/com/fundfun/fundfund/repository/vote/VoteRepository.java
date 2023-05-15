package com.fundfun.fundfund.repository.vote;

import com.fundfun.fundfund.domain.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoteRepository extends JpaRepository<Vote, UUID> {
}
