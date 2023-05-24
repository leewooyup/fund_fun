package com.fundfun.fundfund.controller.post;

import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.domain.user.UserAdapter;
import com.fundfun.fundfund.domain.user.UserDTO;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.dto.reply.ReplyDto;
import com.fundfun.fundfund.service.post.PostService;
import com.fundfun.fundfund.service.reply.ReplyService;
import com.fundfun.fundfund.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final static int PAGE_COUNT = 10;
    private final static int BLOCK_COUNT = 4;

    private final PostService postService;

    private final UserService userService;

    private final ReplyService replyService;
    private final ModelMapper modelMapper;

    /**
     * 아이디어 전체조회 화면 이동
     * : 페이징 처리 없는 버전
     */
//    @GetMapping("/list")
//    public String goIdeaList(Model model, @AuthenticationPrincipal UserAdapter adapter){ /*, @RequestParam(defaultValue = "1") int nowPage*/
//        /*Pageable page = PageRequest.of((nowPage - 1), PAGE_COUNT, Sort.Direction.DESC, "createdAt");
//        Page<PostDto> pageList = postService.selectAll(page);
//
//        int temp = (nowPage - 1) % BLOCK_COUNT;
//        int startPage = nowPage - temp;
//
//        model.addAttribute("postList", pageList);
//
//        model.addAttribute("blockCount", BLOCK_COUNT);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("nowPage", nowPage);*/
//
//        List<PostDto> postList = postService.selectAll();
//        model.addAttribute("postList", postList);
//        if(adapter!=null){
//            model.addAttribute("userInfo", modelMapper.map(adapter.getUser(), UserDTO.class));
//        }
//        else if(adapter == null){
//            model.addAttribute("userInfo", null);
//        }
//
//        return "post/list";
//    }

    /**
     * 아이디어 전체조회 화면 이동
     * : 페이징 처리 없는 버전
     */
    @GetMapping("/list")
    public String goIdeaList(Model model, @AuthenticationPrincipal UserAdapter adapter, @RequestParam(defaultValue = "1") int nowPage){
        Pageable page = PageRequest.of((nowPage - 1), PAGE_COUNT, Sort.Direction.DESC, "createdAt");
        Page<PostDto> postList = postService.selectAll(page);

        int temp = (nowPage - 1) % BLOCK_COUNT;
        int startPage = nowPage - temp;

        model.addAttribute("postList", postList);

        model.addAttribute("blockCount", BLOCK_COUNT);
        model.addAttribute("startPage", startPage);
        model.addAttribute("nowPage", nowPage);

        if(adapter!=null){
            model.addAttribute("userInfo", modelMapper.map(adapter.getUser(), UserDTO.class));
        }
        else if(adapter == null){
            model.addAttribute("userInfo", null);
        }

        return "post/list";
    }

    /**
     * 아이디어 인기순 정렬
     */
    @GetMapping("/list/popular")
    public String popularIdeaList(Model model, @AuthenticationPrincipal UserAdapter adapter, @RequestParam(defaultValue = "1") int nowPage) {
        Pageable page = PageRequest.of((nowPage - 1), PAGE_COUNT, Sort.Direction.DESC, "likePost");
        Page<PostDto> postDtoList = postService.getPostsOrderByLikes(page);
        //Page<PostDto> postList = new PageImpl<>(postDtoList);

        int temp = (nowPage - 1) % BLOCK_COUNT;
        int startPage = nowPage - temp;

        model.addAttribute("postList", postDtoList);

        model.addAttribute("blockCount", BLOCK_COUNT);
        model.addAttribute("startPage", startPage);
        model.addAttribute("nowPage", nowPage);

        if(adapter!=null){
            model.addAttribute("userInfo", modelMapper.map(adapter.getUser(), UserDTO.class));
        }
        else if(adapter == null){
            model.addAttribute("userInfo", null);
        }

        return "post/list";
    }

    /**
     * 가상품만 보기
     */
    @GetMapping("/list/preproduct")
    public String preproductList(Model model, @AuthenticationPrincipal UserAdapter adapter, @RequestParam(defaultValue = "1") int nowPage) {
        Pageable page = PageRequest.of((nowPage - 1), PAGE_COUNT, Sort.Direction.DESC, "createdAt");
        Page<PostDto> postDtoList = postService.selectPostByStatus(StPost.PREPRODUCT, page);
        //Page<PostDto> postList = new PageImpl<>(postDtoList);

        int temp = (nowPage - 1) % BLOCK_COUNT;
        int startPage = nowPage - temp;

        model.addAttribute("postList", postDtoList);

        model.addAttribute("blockCount", BLOCK_COUNT);
        model.addAttribute("startPage", startPage);
        model.addAttribute("nowPage", nowPage);

        if(adapter!=null){
            model.addAttribute("userInfo", modelMapper.map(adapter.getUser(), UserDTO.class));
        }
        else if(adapter == null){
            model.addAttribute("userInfo", null);
        }

        return "post/list";
    }

    /**
     * 키워드로 검색
     */
    @GetMapping("/list/searchResult")
    public String searchList(Model model, @RequestParam String keyword, @AuthenticationPrincipal UserAdapter adapter, @RequestParam(defaultValue = "1") int nowPage) {
        Pageable page = PageRequest.of((nowPage - 1), PAGE_COUNT, Sort.Direction.DESC, "createdAt");
        Page<PostDto> postDtoList = postService.selectPostByKeyword(keyword, page);
        //Page<PostDto> postList = new PageImpl<>(postDtoList);

        int temp = (nowPage - 1) % BLOCK_COUNT;
        int startPage = nowPage - temp;

        model.addAttribute("postList", postDtoList);

        model.addAttribute("keyword", keyword);

        model.addAttribute("blockCount", BLOCK_COUNT);
        model.addAttribute("startPage", startPage);
        model.addAttribute("nowPage", nowPage);

        if(adapter!=null){
            model.addAttribute("userInfo", modelMapper.map(adapter.getUser(), UserDTO.class));
        }
        else if(adapter == null){
            model.addAttribute("userInfo", null);
        }

        return "post/list";
    }

    /**
     * 아이디어 상세조회
     */
    @GetMapping("/detail/{id}")
    public String goIdeaDetail(@PathVariable UUID id, Model model, @AuthenticationPrincipal UserAdapter adapter) {
        PostDto postDto = postService.selectPostById(id);
        if (postDto != null) {
            model.addAttribute("postDto", postDto);
            // 해당 id의 게시물을 model에 담아 반환

            List<ReplyDto> replyList = replyService.selectReplyByPostId(postDto);
            if (replyList != null) {
                model.addAttribute("replyList", replyList);
                model.addAttribute("replyCount", replyService.countByPostId(id));
            }
            //해당 게시물에 댓글이 있다면 반환

            if(adapter!=null) {
                model.addAttribute("userInfo", modelMapper.map(adapter.getUser(), UserDTO.class));
            }
            else if(adapter == null){
                model.addAttribute("userInfo", null);
            }
            //수정 및 삭제 버튼 유무 결정하기 위한 유저 정보 반환

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
    public String addLike(@PathVariable UUID id, Model model, @AuthenticationPrincipal UserAdapter adapter) {
        //좋아요 개수 추가 및 유저 정보 변경
        postService.addLike(id, adapter.getUser());

        PostDto postDto = postService.selectPostById(id);
        if (postDto.getLikePost() >= 10 && postDto.getStatusPost() == StPost.EARLY_IDEA) {
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
    public String create(@AuthenticationPrincipal UserAdapter adapter, @Valid PostForm postForm, BindingResult result) {
        if (result.hasErrors()) {
            return "post/write";
        }

        PostDto postDto = new PostDto();
        postDto.setUser(adapter.getUser());
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
    public String goIdeaEdit(@PathVariable UUID postId, Model model, @AuthenticationPrincipal UserAdapter adapter) {
        PostDto postDto = postService.selectPostById(postId);
        Users writer = postDto.getUser();

        if (writer.getId().equals(adapter.getUser().getId())) { // 유저의 아이디와 글쓴이의 아이디가 일치하면 글 수정할 수 있도록 처리
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
        } else {
            throw new RuntimeException("해당 글의 글쓴이만 게시물을 수정할 수 있습니다.");
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
    public String deleteIdea(@PathVariable UUID id, @AuthenticationPrincipal UserAdapter adapter) {
        PostDto postDto = postService.selectPostById(id);
        Users writer = postDto.getUser();

        if (writer.getId().equals(adapter.getUser().getId())){
            postService.deletePost(id);
            return "redirect:/post/list";
        } else {
            throw new RuntimeException("해당 글의 글쓴이만 게시물을 삭제할 수 있습니다.");
        }
    }
}