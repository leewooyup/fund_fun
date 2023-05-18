package com.fundfun.fundfund.service.reply;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.reply.Reply;
import com.fundfun.fundfund.domain.user.Users;

import java.util.List;
import java.util.UUID;

public interface ReplyService {
    //댓글 전체 조회
    List<Reply> selectAll();

    //게시글 아이디로 댓글 조회(최신순)
    List<Reply> selectReplyByPostId(UUID postId);

    //유저 아이디로 댓글 조회
    List<Reply> selectReplyByUserId(UUID userId);

    //댓글 등록
    void insertReply(Post post, Users user, String content);

    //댓글 수정
    void updateReply(Reply reply);

    //댓글 삭제
    void deleteReply(UUID replyId);

}
