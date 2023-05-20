package com.fundfun.fundfund.controller.post;

import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

//    아이디어 전체조회 화면 이동

    @GetMapping("/list")
    public String goIdeaList(Model model) {
        List<PostDto> postList = postService.selectAll();
        model.addAttribute("postList", postList);
        return "post/list";
    }


//    아이디어 상세조회 화면 이동

    @GetMapping("/detail/{id}")
    public String goIdeaDetail(@PathVariable UUID id, Model model) {
        PostDto post = postService.selectPostById(id);
        if (post!=null) {
            model.addAttribute("post", post);
            return "post/detail";
        } else {
            // 존재하지 않는 게시물일 경우 처리
            return "redirect:/post/list";
        }
    }

//    아이디어 작성 및 수정 화면 이동

    @GetMapping("/write")
    public String goIdeaWrite(Model model) {
        model.addAttribute("form", new PostForm());
        return "post/write";
    }

    //아이디어작성
    @PostMapping("/write")
    public String create(PostForm form) {
        PostDto postDto = new PostDto();
        postDto.setTitle(form.getTitle());
        postDto.setContentPost(form.getContentPost());
        postDto.setCategoryPost("주식형");
        postDto.setStatusPost(StPost.EARLY_IDEA);
        postService.createPost(postDto);
        return "redirect:/post/list";
    }

    //아이디어수정
    @GetMapping("/edit/{postId}")
    public String goIdeaEdit(@PathVariable UUID postId, Model model) {
        PostDto postDto = postService.selectPostById(postId);
        if (postDto!=null) {
//            Post post = postDto.get();
            PostForm form = new PostForm();
//            form.setTitle(post.getTitle());
//            form.setContentPost(post.getContentPost());
            postService.updatePost(postId, postDto.getTitle(), postDto.getContentPost());
            model.addAttribute("postId", postId);
            model.addAttribute("form", form);
            return "post/edit";
        } else {
            // 존재하지 않는 게시물일 경우 처리
            return "redirect:/post/list";
        }
    }

    @GetMapping("/delete")
    public String deleteIdea(@PathVariable UUID postId){
        postService.deletePost(postId);
        return "redirect:/post/list";
    }
}