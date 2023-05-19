package com.fundfun.fundfund.dto.portfolio;

import lombok.Data;

import java.util.UUID;

@Data
public class PortfolioDto {
    private UUID voteId;

    private UUID userId;

    private UUID postId;

    private String title;
    private String ContentPortfolio;
    private String warnLevel;
    private int beneRatio;
}
