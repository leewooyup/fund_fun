package com.fundfun.fundfund.dto.opinion;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.UUID;

@Data
public class OpinionDto {
    //private UUID opinionId;

    private UUID voteId;

    private UUID userId;

    private UUID voted_for;
}
