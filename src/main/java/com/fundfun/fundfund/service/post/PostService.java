package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.dto.post.PostDto;

import java.util.List;
import java.util.UUID;

public interface PostService {

    //포스트 생성
    Post createPost(PostDto postDto);


    //전체 게시물 조회
    List<PostDto> selectAll();

    //작성자로 게시물 조회
    List<PostDto> selectPostByUserId(UUID postId);


    //제목으로 게시물 검색
    List<PostDto> selectPostByKeyword(String keyword);


    //상태로 게시물 조회(최신순)
    List<PostDto> selectPostByStatus(StPost status);


    //카테고리로 게시물 조회
    List<PostDto> selectPostByCategory(String category);

    //게시물 삭제
    void deletePost(UUID postId);

    //게시물 수정
    void updatePost(UUID postId, String newTitle, String newContent);

    //좋아요 높은 순으로 게시물 정렬
    List<PostDto> getPostsOrderByLikes();

    //게시물에 좋아요 추가
    void addLike(UUID postId);

    //게시물의 상태 변경
    void updateStatus(PostDto post, StPost status);


    //게시물의 좋아요 개수 조회
    int getLikeById(UUID postId);

}

