package com.fundfun.fundfund.controller.post;

import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.dto.reply.ReplyDto;
import com.fundfun.fundfund.service.post.PostService;
import com.fundfun.fundfund.service.reply.ReplyService;
import com.fundfun.fundfund.service.user.CustomUserDetailService;
import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final UserService userService;

    private final ReplyService replyService;

    /**
     * 아이디어 전체조회 화면 이동
     * -> RestController로 옮겨야
     */
    @GetMapping("/list")
    public String goIdeaList(Model model) {
        List<PostDto> postList = postService.selectAll();
        model.addAttribute("postList", postList);
        return "post/list";
    }


    /**
     * 아이디어 상세조회
     */
    @GetMapping("/detail/{id}")
    public String goIdeaDetail(@PathVariable UUID id, Model model) {
        PostDto postDto = postService.selectPostById(id);
        if (postDto != null) {
            model.addAttribute("postDto", postDto);
            // 해당 id의 게시물을 model에 담아 반환

            List<ReplyDto> replyList = replyService.selectReplyByPostId(postDto);
            if(replyList != null){
                model.addAttribute("replyList", replyList);
                model.addAttribute("replyCount", replyService.countByPostId(id));
            }
            //해당 게시물에 댓글이 있다면 반환

            return "post/detail";
        } else {
            // 존재하지 않는 게시물일 경우 처리
            return "redirect:/post/list";
        }
    }

    /**
     * 아이디어에 좋아요 누르기
     */
    @GetMapping("/detail/{id}/like")
    public String addLike(@PathVariable UUID id, Model model) {
        //좋아요 개수 추가
        postService.addLike(id);

        PostDto postDto = postService.selectPostById(id);
        if(postDto.getLikePost()>=10 && postDto.getStatusPost()==StPost.EARLY_IDEA){
            postService.updateStatus(postDto, StPost.PREPRODUCT);
        } //좋아요가 일정 개수 넘어가면 상태 변경 및 연결된 투표 생성

        return "redirect:/post/detail/{id}";
    }

    /**
     * 아이디어 작성
     */
    @GetMapping("/write")
    public String goIdeaWrite(PostForm postForm, Model model) {
        return "post/write";
    }

    @PostMapping("/write")
    public String create(@AuthenticationPrincipal Users user, @Valid PostForm postForm, BindingResult result) {
        System.out.println("user.getEmail() = " + user.getEmail());
        if (result.hasErrors()) {
            return "post/write";
        }
        Users u = userService.findByEmail(user.getEmail()).orElse(null);
        System.out.println("u = " + u.getEmail());
        PostDto postDto = new PostDto();
        postDto.setUser(u);
        postDto.setTitle(postForm.getTitle());
        postDto.setContentPost(postForm.getContentPost());
        postDto.setCategoryPost("주식형");
        postDto.setStatusPost(StPost.EARLY_IDEA);
        postService.createPost(postDto);
        return "redirect:/post/list";
    }

    /**
     * 아이디어 수정
     */
    @GetMapping("/edit/{postId}")
    public String goIdeaEdit(@PathVariable UUID postId, Model model) {
        PostDto postDto = postService.selectPostById(postId);
        if (postDto != null) {
            PostForm postForm = new PostForm();
            //postForm.setId(postDto.getId());
            postForm.setTitle(postDto.getTitle());
            postForm.setContentPost(postDto.getContentPost());

            model.addAttribute("postId", postId);
            model.addAttribute("postForm", postForm);
            return "post/edit";
        } else {
            // 존재하지 않는 게시물일 경우 처리
            return "redirect:/post/list";
        }
    }

    /**
     * 아이디어 수정
     */
    @PostMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") UUID postId, @Valid PostForm postForm, BindingResult result) {
        System.out.println("postId = " + postId);
        if (result.hasErrors()) {
            return "redirect:/post/edit/" + postId;
        }
//        PostDto postDto = new PostDto();
//        postDto.setTitle(postForm.getTitle());
//        postDto.setContentPost(postForm.getContentPost());
        postService.updatePost(postId, postForm.getTitle(), postForm.getContentPost());
        return "redirect:/post/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteIdea(@PathVariable UUID id) {

        postService.deletePost(id);
        return "redirect:/post/list";
    }
}