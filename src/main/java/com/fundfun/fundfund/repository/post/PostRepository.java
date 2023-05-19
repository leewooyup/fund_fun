package com.fundfun.fundfund.repository.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.post.StPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findByTitleContaining(String title);

    List<Post> findByStatusPost(StPost status);

    List<Post> findByCategoryPost(String category);

}