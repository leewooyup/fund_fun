package com.fundfun.fundfund.repository.post;


import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.domain.user.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    Page<Post> findByTitleContaining(String title, Pageable pageable);

    Page<Post> findByStatusPost(StPost status, Pageable pageable);

    Page<Post> findByCategoryPost(String category, Pageable pageable);

    List<Post> findByUser(Users user);

}