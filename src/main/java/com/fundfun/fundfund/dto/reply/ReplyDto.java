package com.fundfun.fundfund.dto.reply;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.user.Users;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ReplyDto {
    private UUID id;
    private Post post;
    private Users user;
    private String contentReply;
    private LocalDateTime createdAt;
    // 댓글 작성일 - 가독성 위해 파싱
    private String writeTime = null;
    private LocalDateTime updatedAt;
}
