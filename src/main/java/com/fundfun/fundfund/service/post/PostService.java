package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.post.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {
    //전체 게시물 조회
    List<Post> selectAll();

    //제목으로 게시물 조회
    List<Post> selectPostByKeyword(String keyword);

    //작성자로 게시물 조회
    List<Post> selectPostByUserId(UUID userId);

    //상태로 게시물 조회
    List<Post> selectPostByStatus(String status);

    //카테고리로 게시물 조회
    List<Post> selectPostByCategory(String category);

    //게시물 생성
    void createPost(Post post);

    //게시물 삭제
    void deletePost(Post post);

    //게시물 수정
    void updatePost(Post post);

    //좋아요 높은 순으로 게시물 정렬
    List<Post> getPostsOrderByLikes();

    //좋아요 100개 이상 넘어갔을 때 상태 변경
    void updatePostStatus(Post post);
}