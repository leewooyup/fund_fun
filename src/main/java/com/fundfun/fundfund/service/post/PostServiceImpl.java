package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.repository.post.PostRepository;
import com.fundfun.fundfund.repository.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRep;
    private final VoteRepository voteRep;
    private final ModelMapper modelMapper;

    //게시물 생성
    public void createPost(Post post) {
        postRep.save(post);
    }

    //전체 게시물 조회
    public List<Post> selectAll() {
        return postRep.findAll();
    }

    //제목으로 게시물 조회
    public List<Post> selectPostByKeyword(String keyword) {
        return postRep.findByTitleContaining(keyword);

    }

    //작성자로 게시물 조회
    public Optional<Post> selectPostByUserId(UUID userId) {
        return postRep.findById(userId);
    }

    //상태로 게시물 조회
    public List<Post> selectPostByStatus(String status) {
        return postRep.findByStatusPost(status);
    }

    //카테고리로 게시물 조회
    public List<Post> selectPostByCategory(String category) {
        return postRep.findByCategoryPost(category);
    }

    //게시물 삭제
    public void delete(Post post) {

        postRep.delete(post);

    }


//게시물 수정

    public void updatePost(Post post) {
        Post existingPost = postRep.findById(post.getId()).orElse(null);
        if (existingPost != null) {
            // 변경할 필드값을 업데이트합니다.
            existingPost.setTitle(post.getTitle());
            existingPost.setContentPost(post.getContentPost());

            // 게시물을 저장하여 업데이트합니다.
            postRep.save(existingPost);

        } else {
            throw new RuntimeException("검색한 게시물이 존재하지 않습니다.");
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

    //게시물에 좋아요 추가
    @Override
    public void addLike(UUID postId) {
        Post post = postRep.findById(postId).orElse(null);

        if(post == null)
            throw new RuntimeException("해당 게시물이 존재하지 않습니다.");

        post.setLikePost(post.getLikePost()+1); //좋아요 + 1
        postRep.save(post);
        //updateStatus(post, StPost.PREPRODUCT); //좋아요 갯수 체크 및 상태 변경
        //
    }


    public void updateStatus(Post post, StPost status){
        post.setStatusPost(status);
        Vote vote = new Vote();
        vote.linkPost(post);
        voteRep.save(vote);
        post.linkVote(vote);
        postRep.save(post);
    }

    //좋아요 100개 이상 시 상태 변경
    public void updatePostStatus(Post post) {
        if (post.getLikePost() >= 10) {
            //post.setStatusPost();

            postRep.save(post);
        }
    }

    //게시물의 좋아요 개수 조회
    @Override
    public int getLikeById(UUID postId) {
        Post post = postRep.findById(postId).orElse(null);
        if(post==null)
            throw new RuntimeException("해당 게시물이 존재하지 않습니다.");

        return post.getLikePost();
    }
}
