package com.fundfun.fundfund.repository.opinion;

import com.fundfun.fundfund.domain.opinion.Opinion;
import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, UUID> {
    List<Opinion> findByVote(Vote vote);
    List<Opinion> findByVotedFor(Portfolio portfolio);

    @Query(value = "select o from Opinion o where o.vote = ?1 and o.user = ?2")
    Opinion findByVoteIdAndUserId(Vote vote, Users user);
}
