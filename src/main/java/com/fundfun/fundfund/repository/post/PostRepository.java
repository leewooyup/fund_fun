package com.fundfun.fundfund.repository.post;

import com.fundfun.fundfund.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
}
