package com.fundfun.fundfund.service.reply;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.reply.Reply;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.dto.reply.ReplyDto;
import com.fundfun.fundfund.repository.post.PostRepository;
import com.fundfun.fundfund.repository.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{
    @Autowired
    private final ReplyRepository replyRep;

    @Autowired
    private final PostRepository postRep;

    @Autowired
    private final ModelMapper modelMapper;

    //댓글 아이디로 댓글 조회
    public ReplyDto selectById(UUID replyId){
        Reply reply = replyRep.findById(replyId).orElse(null);
        if(reply == null)
            throw new RuntimeException("해당 댓글이 존재하지 않습니다.");

        ReplyDto replyDto = modelMapper.map(reply, ReplyDto.class);

        return replyDto;
    }

    //댓글 전체 조회
    @Override
    public List<ReplyDto> selectAll() {
        //List<ReplyDto> list = replyRep.findAll().stream().map(reply -> modelMapper.map(reply, ReplyDto.class)).collect(Collectors.toList());
        return replyRep.findAll().stream().map(reply -> modelMapper.map(reply, ReplyDto.class)).collect(Collectors.toList());
    }

    //게시글 아이디로 댓글 조회(최신순)
    @Override
    public List<ReplyDto> selectReplyByPostId(PostDto postDto) {
        //Post p = postRep.findById(postId).orElse(null);
        Post p = modelMapper.map(postDto, Post.class);
        if(p == null)
            throw new RuntimeException("해당 게시물이 존재하지 않습니다.");

        return replyRep.findNewestReplyByPost(p).stream().map(reply -> modelMapper.map(reply, ReplyDto.class)).collect(Collectors.toList());
    }

    //유저 아이디로 댓글 조회
    @Override
    public List<ReplyDto> selectReplyByUser(Users user) {
        return replyRep.findByUser(user).stream().map(reply -> modelMapper.map(reply, ReplyDto.class)).collect(Collectors.toList());
    }

    @Override
    public void insertReply(ReplyDto replyDto) {
        Reply reply = modelMapper.map(replyDto, Reply.class);
        // dto -> entity (link 과정이 필요 없다)

        replyRep.save(reply);
    }

    //댓글 수정
    @Override
    public void updateReply(ReplyDto replyDto) {
        Reply existingReply = replyRep.findById(replyDto.getId()).orElse(null);
        if(existingReply==null)
            throw new RuntimeException("존재하지 않는 댓글을 수정할 수 없습니다.");

        existingReply.setContentReply(replyDto.getContentReply());
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

    @Override
    public int countByPostId(UUID postId) {
        List<Reply> list = replyRep.findByPostId(postId);
        return list.size();
    }
}
