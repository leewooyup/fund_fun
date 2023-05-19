package com.fundfun.fundfund.dto.reply;

import lombok.Data;

import java.util.UUID;

@Data
public class ReplyDto {
    private UUID id;
    private UUID postId;
    private UUID userId;
    private String contentReply;
}
