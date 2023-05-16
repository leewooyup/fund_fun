package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.post.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceImplTest {
    @Autowired
    PostService postService;

    @Test
    void selectPostByKeyword() {
        String keyword = "example";
        List<Post> posts = postService.selectPostByKeyword(keyword);

    }
}