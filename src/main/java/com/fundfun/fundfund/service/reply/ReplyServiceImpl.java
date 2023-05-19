package com.fundfun.fundfund.service.reply;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.reply.Reply;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.repository.post.PostRepository;
import com.fundfun.fundfund.repository.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{
    @Autowired
    private final ReplyRepository replyRep;

    @Autowired
    private final PostRepository postRep;

    //댓글 전체 조회
    @Override
    public List<Reply> selectAll() {
        return replyRep.findAll();
    }

    //게시글 아이디로 댓글 조회(최신순)
    @Override
    public List<Reply> selectReplyByPostId(UUID postId) {
        Post p = postRep.findById(postId).orElse(null);
        if(p == null)
            throw new RuntimeException("해당 게시물이 존재하지 않습니다.");

        return replyRep.findNewestReplyByPost(p);
    }

    //유저 아이디로 댓글 조회
    @Override
    public List<Reply> selectReplyByUserId(UUID userId) {
        return replyRep.findByUserId(userId);
    }

    //댓글 등록
    @Override
    public void insertReply(Post post, Users user, String content) {
        Reply reply = new Reply();
        reply.linkPost(post);
        reply.linkUser(user);
        reply.setContentReply(content);

        replyRep.save(reply);
    }

    //댓글 수정
    @Override
    public void updateReply(Reply reply) {
        Reply existingReply = replyRep.findById(reply.getId()).orElse(null);
        if(existingReply==null)
            throw new RuntimeException("존재하지 않는 댓글을 수정할 수 없습니다.");

        existingReply.setContentReply(reply.getContentReply());
        replyRep.save(existingReply);
    }

    //댓글 삭제
    @Override
    public void deleteReply(UUID replyId) {
        Reply re = replyRep.findById(replyId).orElse(null);
        if(re == null)
            throw new RuntimeException("존재하지 않는 댓글을 삭제할 수 없습니다.");
        replyRep.delete(re);
    }
}
