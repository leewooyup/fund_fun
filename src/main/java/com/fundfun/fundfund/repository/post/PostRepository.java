package com.fundfun.fundfund.repository.post;

import com.fundfun.fundfund.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    //키워드가 포함된 제목으로 찾기
    List<Post> findByTitleContain(String keyword);
    //상태로 찾기
    List<Post> findByStatusPost(String status);
    //카테고리로 찾기
    List<Post> findByCategory(String category);

    Post findByTitle(String title);
}