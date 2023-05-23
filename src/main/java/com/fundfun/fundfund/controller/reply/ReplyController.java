package com.fundfun.fundfund.controller.reply;

import com.fundfun.fundfund.domain.post.Post;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String writeReply(@AuthenticationPrincipal Users user, @RequestParam("id") UUID id, @RequestParam("contentReply") String contentReply){
        ReplyDto replyDto = new ReplyDto();
        replyDto.setContentReply(contentReply);
        replyDto.setPost(modelMapper.map(postService.selectPostById(id), Post.class));
        replyDto.setUser(user);
        replyService.insertReply(replyDto);

        return "redirect:/post/detail/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteReply(@AuthenticationPrincipal Users user, @PathVariable UUID id){
        replyService.deleteReply(id);

        return "redirect:/post/detail/" + id;
    }
}
