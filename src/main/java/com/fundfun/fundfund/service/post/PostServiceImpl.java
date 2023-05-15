package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRep;

    //전체 게시물 조회
    public List<Post> selectAll(){
        return postRep.findAll();
    }

    //제목으로 게시물 조회
    public List<Post> selectPostByKeyword(String keyword){
        return null;
    }

    //작성자로 게시물 조회
    public List<Post> selectPostByUserId(UUID userId){
        return null;
    }

    //상태로 게시물 조회
    public List<Post> selectPostByStatus(String status){
        return null;
    }

    //카테고리로 게시물 조회
    public List<Post> selectPostByCategory(String category){
        return null;
    }

    //게시물 생성
    public void createPost(Post post){

        postRep.save(post);
    }

    //게시물 삭제
    public void deletePost(Post post){

        postRep.delete(post);

    }

//게시물 수정

    public void updatePost(Post post){

        Optional<Post> optionalPost = postRep.findById(post.getId());
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            // 변경할 필드값을 업데이트합니다.
            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());

            // 게시물을 저장하여 업데이트합니다.
            postRep.save(existingPost);

        }

    }

    //좋아요 순으로 게시물 정렬
    public List<Post> getPostsOrderByLikes(){
        Post post = Post.builder()
                .;
        return postRep.findAll(Sort.by(Sort.Direction.DESC, "like"));

    }

//좋아요 100 개 이상 시 상태 변경

    public void updatePostStatus(Post post){

        if(post.getLike()>= 100){
            post.setStatus("update preproduct");

            postRep.save(post);

        }

    }
}