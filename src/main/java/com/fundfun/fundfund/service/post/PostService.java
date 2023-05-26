package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.dto.post.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;


public interface PostService {

    //포스트 생성
    Post createPost(PostDto postDto);


    //전체 게시물 조회
    List<PostDto> selectAll();

    Page<PostDto> selectAll(Pageable pageable);

    //작성자로 게시물 조회
    PostDto selectPostById(UUID postId);


    //제목으로 게시물 검색
    Page<PostDto> selectPostByKeyword(String keyword, Pageable pageable);


    //상태로 게시물 조회(최신순)
    Page<PostDto> selectPostByStatus(StPost status, Pageable pageable);


    //카테고리로 게시물 조회
    Page<PostDto> selectPostByCategory(String category, Pageable pageable);

    //userId로 게시물 조회
    List<PostDto> selectPostByUserId(UUID userId);

    //게시물 삭제
    void deletePost(UUID postId);

    //게시물 수정
    void updatePost(UUID postId, String newTitle, String newContent);

    //좋아요 높은 순으로 게시물 정렬
    Page<PostDto> getPostsOrderByLikes(Pageable pageable);

    //게시물에 좋아요 추가
    void addLike(UUID postId, Users user);

    //게시물의 상태 변경
    void updateStatus(PostDto post, StPost status);


    //게시물의 좋아요 개수 조회
    int getLikeById(UUID postId);

}
