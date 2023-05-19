package com.fundfun.fundfund.dto.opinion;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.UUID;

@Data
public class OpinionDto {
    private UUID id;

    private UUID voteId; // vote와 연관관계

    private UUID userId; // user와 연관관계

    private UUID votedFor; // portfolio와 연관관계
}
