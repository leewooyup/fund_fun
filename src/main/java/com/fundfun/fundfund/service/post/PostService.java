package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.post.StPost;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostService {

    //전체 게시물 조회
    List<Post> selectAll();

    //제목으로 게시물 조회
    List<Post> selectPostByKeyword(String keyword);

    //작성자로 게시물 조회
    Optional<Post> selectPostByUserId(UUID userId);

    //상태로 게시물 조회
    List<Post> selectPostByStatus(String status);

    //카테고리로 게시물 조회
    List<Post> selectPostByCategory(String category);

    //게시물 생성
    void createPost(Post post);

    //게시물 삭제
   void delete(Post post);

    //게시물 수정
    void updatePost(Post post);

    //좋아요 높은 순으로 게시물 정렬
    List<Post> getPostsOrderByLikes();

    //게시물에 좋아요 추가
    void addLike(UUID postId);

    //게시물의 상태 변경
    void updateStatus(Post post, StPost status);

    //좋아요 100개 이상 넘어갔을 때 상태 변경
    void updatePostStatus(Post post);

    //게시물의 좋아요 개수 조회
    int getLikeById(UUID postId);

}
