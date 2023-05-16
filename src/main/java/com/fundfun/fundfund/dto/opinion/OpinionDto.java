package com.fundfun.fundfund.dto.opinion;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.UUID;

public class OpinionDto {
    private UUID opinionId;

    private UUID voteId;

    private UUID userId;

    private UUID voted_for;
}
