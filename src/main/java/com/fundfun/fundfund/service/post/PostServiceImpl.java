package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.post.QPost;
import com.fundfun.fundfund.repository.post.PostRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

    private final JPAQueryFactory query;
    private final PostRepository postRep;
    private final ModelMapper modelMapper;


    //전체 게시물 조회
    public List<Post> selectAll() {
        return postRep.findAll();
    }

    //제목으로 게시물 조회
    public List<Post> selectPostByKeyword(String keyword) {
        QPost p = QPost.post;
        //List<Post> list = query.select().from(p).w
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

    fin
    //카테고리로 게시물 조회
    public List<Post> selectPostByCategory(String categoryPost) {
        QPost qpost = QPost.post;
        return query.selectFrom(qpost)
                .where(qpost.categoryPost.eq(categoryPost))
                .fetch();
    }

    //게시물 생성
    public void createPost(Post post) {
        postRep.save(post);
    }

    //게시물 삭제
    public void deletePost(Post post) {

        postRep.delete(post);

    }

    public void updatePost(Post post) {
        Post existingPost = postRep.findById(post.getId()).orElse(null);
        if (existingPost != null) {
            //변경 필드값 업데이트
            existingPost.setTitle(post.getTitle());

            //게시물저장
            postRep.save(existingPost);
        }
    }


    //좋아요 순으로 게시물 정렬
    public List<Post> getPostsOrderByLikes() {

        Post post = Post.builder()
                .title("Example Title")
                .contentPost("Example Content")
                .build();

        return postRep.findAll(Sort.by(Sort.Direction.DESC, "like"));

    }

//좋아요 100 개 이상 시 상태 변경

    public void updatePostStatus(Post post) {

        if (post.getLikePost() >= 100) {
            post.setStatusPost("update preproduct");

            postRep.save(post);

        }

    }
}