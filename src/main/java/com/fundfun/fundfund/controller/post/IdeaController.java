/*
package com.fundfun.fundfund.controller.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.service.post.PostService;
import com.fundfun.fundfund.service.post.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class IdeaController {

    private final PostService postService;

    */
/**
     * 아이디어 전체조회 화면 이동
     * *//*

    @GetMapping("/idea/ideaList")
    public String goIdeaList(Model model) {
        List<Post> posts = postService.selectAll();
        model.addAttribute("posts", posts);
        return "idea/ideaList";
    }


    */
/**
     * 아이디어 상세조회 화면 이동
     * *//*

    @GetMapping("/idea/ideaDetail")
    public String goIdeaDetail(@PathVariable UUID postId, Model model) {
        Optional<Post> postOptional = postService.selectPostByUserId(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            model.addAttribute("post", post);
            return "idea/ideaDetail";
        } else {
            // 존재하지 않는 게시물일 경우 처리
            return "redirect:/idea/ideaList";
        }
    }

    */
/**
     * 아이디어 작성 및 수정 화면 이동
     * *//*

    @GetMapping("/idea/ideaWrite")
        public String goIdeaWrite(Model model) {
            model.addAttribute("form", new PostForm());
            return "idea/ideaWrite";
    }

    //아이디어작성
    @PostMapping("/idea/ideaWrite")
    public String create(PostForm form) {
        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setContentPost(form.getContentPost());
        postService.createPost(post);
        return "redirect:/idea/ideaList";
    }

    //아이디어수정
    @GetMapping("/idea/ideaEdit/{postId}")
    public String goIdeaEdit(@PathVariable UUID postId, Model model) {
        Optional<Post> postOptional = postService.selectPostByUserId(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            PostForm form = new PostForm();
            form.setTitle(post.getTitle());
            form.setContentPost(post.getContentPost());
            model.addAttribute("postId", postId);
            model.addAttribute("form", form);
            return "idea/ideaEdit";
        } else {
            // 존재하지 않는 게시물일 경우 처리
            return "redirect:/idea/ideaList";
        }


}
}







*/
