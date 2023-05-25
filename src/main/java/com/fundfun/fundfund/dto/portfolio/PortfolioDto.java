package com.fundfun.fundfund.dto.portfolio;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import lombok.Data;

import java.util.UUID;

@Data
public class PortfolioDto {

    private UUID id;
    private UUID voteId;
    private UUID userId;
    private UUID postId;
    private String title;
    private String contentPortfolio;
    private String warnLevel;
    private float beneRatio;

}


