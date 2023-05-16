package com.fundfun.fundfund.repository.opinion;

import com.fundfun.fundfund.domain.opinion.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, UUID> {
    List<Opinion> findByVoteId(UUID voteId);
    List<Opinion> findByVotedFor(UUID votedFor);
}
