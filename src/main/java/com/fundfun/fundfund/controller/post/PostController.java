package com.fundfun.fundfund.controller.post;

import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 아이디어 전체조회 화면 이동
     * -> RestController로 옮겨야
     * */
    @GetMapping("/list")
    public String goIdeaList(Model model) {
        List<PostDto> postList = postService.selectAll();
        model.addAttribute("postList", postList);
        return "post/list";
    }


    /**
     * 아이디어 상세조회
     * */
    @GetMapping("/detail/{id}")
    public String goIdeaDetail(@PathVariable UUID id, Model model) {
        PostDto postDto = postService.selectPostById(id);
        if (postDto != null) {
            model.addAttribute("postDto", postDto);
            return "post/detail";
        } else {
            // 존재하지 않는 게시물일 경우 처리
            return "redirect:/post/list";
        }
    }

    /**
     * 아이디어 작성
     * */
    @GetMapping("/detail/{id}/like")
    public String addLike(@PathVariable UUID id, Model model){
        //UUID uuid = UUID.fromString(id);
        postService.addLike(id);
        PostDto postDto = postService.selectPostById(id);
        model.addAttribute("postDto", postDto);

        return "redirect:/post/detail/{id}";
    }

    /**
     * 아이디어 작성
     * */
    @GetMapping("/write")
    public String goIdeaWrite(PostForm postForm, Model model) {
        return "post/write";
    }

    @PostMapping("/write")
    public String create(@Valid PostForm postForm, BindingResult result) {
        if (result.hasErrors()) {
            return "post/write";
        }
        PostDto postDto = new PostDto();
        postDto.setTitle(postForm.getTitle());
        postDto.setContentPost(postForm.getContentPost());
        postDto.setCategoryPost("주식형");
        postDto.setStatusPost(StPost.EARLY_IDEA);
        postService.createPost(postDto);
        return "redirect:/post/list";
    }

    /**
     * 아이디어 수정
     * */
    @GetMapping("/edit/{postId}")
    public String goIdeaEdit(@PathVariable UUID postId, Model model) {
        PostDto postDto = postService.selectPostById(postId);
        if (postDto != null) {
            PostForm postForm = new PostForm();
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
     * 아이디어 삭제
     * */
    @PostMapping("/edit")
    public String edit(UUID postId, @Valid PostForm postForm, BindingResult result) {
        if (result.hasErrors()) {
            return "post/edit";
        }
        PostDto postDto = new PostDto();
        postDto.setTitle(postForm.getTitle());
        postDto.setContentPost(postForm.getContentPost());

        postService.updatePost(postId, postForm.getTitle(), postForm.getContentPost());
        return "redirect:/post/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteIdea(@PathVariable UUID id) {
        postService.deletePost(id);
        return "redirect:/post/list";
    }
}