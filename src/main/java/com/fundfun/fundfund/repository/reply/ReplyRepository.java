package com.fundfun.fundfund.repository.reply;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.reply.Reply;
import com.fundfun.fundfund.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReplyRepository extends JpaRepository<Reply, UUID> {
    List<Reply> findByUser(Users user);

    @Query(value="select r from Reply r where r.post = ?1 order by r.createdAt")
    List<Reply> findNewestReplyByPost(Post post);

    List<Reply> findByPostId(UUID postId);
}
