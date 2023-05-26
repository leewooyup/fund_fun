package com.fundfun.fundfund.service.reply;

import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.dto.reply.ReplyDto;

import java.util.List;
import java.util.UUID;

public interface ReplyService {
    //댓글 아이디로 댓글 조회
    ReplyDto selectById(UUID replyID);

    //댓글 전체 조회
    List<ReplyDto> selectAll();

    //게시글 아이디로 댓글 조회(최신순)
    List<ReplyDto> selectReplyByPostId(PostDto postDto);

    //유저 아이디로 댓글 조회
    List<ReplyDto> selectReplyByUser(Users user);

    //댓글 등록
    //void insertReply(Post post, Users user, String content);
    void insertReply(ReplyDto replyDto);

    //댓글 수정
    void updateReply(ReplyDto replyDto);

    //댓글 삭제
    void deleteReply(UUID replyId);

    //해당 게시글에 달린 댓글의 개수 조회
    int countByPostId(UUID postId);
}
