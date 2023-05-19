package com.fundfun.fundfund.service.reply;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.reply.Reply;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.dto.reply.ReplyDto;
import com.fundfun.fundfund.repository.post.PostRepository;
import com.fundfun.fundfund.service.post.PostService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyServiceImplTest {
    @Autowired
    PostService postService;

    @Autowired
    ReplyService replyService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PostRepository postRep;

    @Test
    public void 댓글_등록(){
        List<PostDto> pList = postService.selectAll();
        Post post = modelMapper.map(pList.get(0), Post.class);
        Users user = null;

        ReplyDto replyDto = new ReplyDto();

        replyDto.setId(UUID.randomUUID());
        replyDto.setPostId(post.getId());
        replyDto.setUserId(null);
        replyDto.setContentReply("댓글!!");

        replyService.insertReply(replyDto);

        System.out.println("replyDto는 " + replyDto.getPostId() + " 게시글에 등록되었습니다.");
        Reply reply = modelMapper.map(replyDto, Reply.class);
        System.out.println("reply entity는 " + reply.getPost().getId() + " 게시글에 등록되었습니다.");
    }

    @Test
    public void 모든_댓글_조회(){
        List<ReplyDto> list = replyService.selectAll();
        for(ReplyDto r : list){
            System.out.println("id = " + r.getId() + ", 내용 = " + r.getContentReply());
        }
    }

    @Test
    public void 댓글_수정(){
        UUID replyId = UUID.fromString("43c76c98-8dfb-452c-b590-7c99ee719325");
        System.out.println("replyId = " + replyId);
        ReplyDto replyDto = replyService.selectById(replyId);


//        reply.setContentReply("수정된 댓글!");
//        replyService.updateReply(reply);
    }

    @Test
    public void 댓글_삭제(){
        //List<Reply> list = replyService.selectAll();
        //Reply reply = list.get(5);
        //replyService.deleteReply(reply.getId());
    }

    @Test
    public void 댓글_수_조회(){
        //List<Reply> list = replyService.selectAll();
        //Reply reply = list.get(3);
        //UUID postId = reply.getPost().getId();

        //System.out.println("댓글 수 = " + replyService.countByPostId(postId));
    }

    @Test
    public void 댓글_최신순_조회(){
        List<PostDto> pList = postService.selectAll();
        PostDto post = pList.get(0);

        //List<Post> postList = postService.selectAll();
        //PostDto post = modelMapper.map(postList.get(0), PostDto.class);

        List<ReplyDto> rList = replyService.selectReplyByPostId(post);
        for(ReplyDto r : rList){
            System.out.println("id = " + r.getId() + ", 내용 = " + r.getContentReply());
        }
    }


}