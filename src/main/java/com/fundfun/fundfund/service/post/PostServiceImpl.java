package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.fundfun.fundfund.modelmapper.ModelMapperConfig;


    @Service
    @RequiredArgsConstructor
    public class PostServiceImpl implements PostService {
        private final PostRepository postRep;
        private final ModelMapper modelMapper;


        //전체 게시물 조회
        public List<Post> selectAll() {
            return postRep.findAll();
        }

        //제목으로 게시물 조회
        public List<Post> selectPostByKeyword(String keyword) {
            return null;
        }

        //작성자로 게시물 조회
        public List<Post> selectPostByUserId(UUID userId) {
            return null;
        }

        //상태로 게시물 조회
        public List<Post> selectPostByStatus(String status) {
            return null;
        }

        //카테고리로 게시물 조회
        public List<Post> selectPostByCategory(String category) {
            return null;
        }

        //게시물 생성
        public void createPost(Post post) {

            postRep.save(post);
        }

        //게시물 삭제
        public void deletePost(Post post) {

            postRep.delete(post);

        }

        @Override
        public void updatePost(Post post) {

        }

        @Override
        public List<Post> getPostsOrderByLikes() {
            return null;
        }

        @Override
        public void updatePostStatus(Post post) {

        }


    }
