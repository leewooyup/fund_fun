package com.fundfun.fundfund.controller.reply;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.user.UserAdapter;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.reply.ReplyDto;
import com.fundfun.fundfund.service.post.PostService;
import com.fundfun.fundfund.service.reply.ReplyService;
import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
    @Autowired
    private final PostService postService;

    @Autowired
    private final UserService userService;

    @Autowired
    private final ReplyService replyService;

    @Autowired
    private final ModelMapper modelMapper;

    /**
     * 댓글 작성
     * */
    @PostMapping("/write")
    public String writeReply(@AuthenticationPrincipal UserAdapter adapter, @RequestParam("id") UUID id, @RequestParam("contentReply") String contentReply){
        ReplyDto replyDto = new ReplyDto();
        replyDto.setContentReply(contentReply);
        replyDto.setPost(modelMapper.map(postService.selectPostById(id), Post.class));
        replyDto.setUser(adapter.getUser());
        replyService.insertReply(replyDto);

        return "redirect:/post/detail/" + id;
    }

    /**
     * 댓글 삭제
     * */
    @GetMapping("/delete/{id}")
    public String deleteReply(@AuthenticationPrincipal UserAdapter adapter, @PathVariable UUID id){
        ReplyDto replyDto = replyService.selectById(id);
        Users writer = replyDto.getUser();

        if (writer.getId().equals(adapter.getUser().getId())){ //
            replyService.deleteReply(id);
            return "redirect:/post/list";
        } else {
            throw new RuntimeException("해당 댓글의 글쓴이만 게시물을 삭제할 수 있습니다.");
        }
    }
}
