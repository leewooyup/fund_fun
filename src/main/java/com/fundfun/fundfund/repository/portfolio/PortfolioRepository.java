package com.fundfun.fundfund.repository.portfolio;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {


    //Portfolio findById(UUID portfolioId);

    List<Portfolio> findByVoteId(UUID voteId);

    List<Portfolio> findByUserId(UUID userId);

    List<Portfolio> findByPostId(UUID postId);
}