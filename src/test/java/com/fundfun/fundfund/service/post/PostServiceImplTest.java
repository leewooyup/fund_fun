package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.service.vote.VoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceImplTest {
    @Autowired
    PostService postService;
    @Autowired
    VoteService voteService;

    @Test
    public void 게시물_생성() throws Exception {
        for (int i = 0; i < 10; i++) {
            Post post = Post.builder()
                    .title("Title " + i)
                    .contentPost("Content " + i)
                    .categoryPost("Category " + i)
                    .build();

            postService.createPost(post);
        };

    }

    @Test
    public void 게시물조회() throws Exception {
        List<Post> list = postService.selectAll();
        for (Post p : list) {
            System.out.println("제목 : " + p.getTitle() + ", 좋아요 수 : " + p.getLikePost() + ", 상태 : " + p.getStatusPost());
        }
    }

    @Test
    public void 제목_게시물조회() throws Exception {
        List<Post> list = postService.selectPostByKeyword(null);
        for (Post p : list) System.out.println(p);
    }

    // @Test
   // public void 작성자_게시물조회() throws Exception {
    //    Optional<Post> olist = postService.selectPostByUserId(UUID.randomUUID());
    //    olist.ifPresent(post -> System.out.println(post));
   // }

    @Test
    public void 상태_게시물조회() throws Exception {
        List<Post> list = postService.selectPostByStatus(null);
        for (Post p : list) System.out.println(p);
    }

    @Test
    public void 카테고리_게시물조회() throws Exception {
        List<Post> list = postService.selectPostByCategory(null);
        for (Post p : list) System.out.println(p);
    }

    @Test
    public void 게시물삭제() throws Exception {

        UUID uuid = postService.selectAll().get(0).getId();
        Optional<Post> postToDelete = postService.selectPostByUserId(uuid);

        // 게시물이 존재하는지 확인
        assertTrue(postToDelete.isPresent());

        // 게시물 삭제
        postToDelete.ifPresent(post -> {
            postService.delete(post);
            assertFalse(postService.selectPostByUserId(uuid).isPresent());
        });
    }

    @Test
    public void 게시물수정() throws Exception {

        UUID uuid = postService.selectAll().get(0).getId();
        // 수정할 게시물 가져오기 (수정할 게시물의 ID 또는 다른 고유 식별자 사용)
        Optional<Post> postToUpdate = postService.selectPostByUserId(uuid);

        // 게시물이 존재하는지 확인
        assertTrue(postToUpdate.isPresent());

        // 게시물 수정
        postToUpdate.ifPresent(post -> {
            // 필드값 업데이트
            post.setTitle("New Title");
            post.setContentPost("New Content");

            // 게시물 업데이트
            postService.updatePost(post);

            // 수정된 게시물 조회 및 확인
            Optional<Post> updatedPost = postService.selectPostByUserId(uuid);
            assertTrue(updatedPost.isPresent());
            assertEquals("New Title", updatedPost.get().getTitle());
            assertEquals("New Content", updatedPost.get().getContentPost());
        });
    }

    @Test
    public void 좋아요정렬() throws Exception {
        List<Post> list = postService.getPostsOrderByLikes();
        for (Post p : list) System.out.println(p);
    }

    @Test
    public void 상태변경() throws Exception {

        UUID uuid = postService.selectAll().get(0).getId();
        // 상태 변경할 게시물 가져오기 (게시물의 ID 또는 다른 고유 식별자 사용)
        Optional<Post> postToUpdateStatus = postService.selectPostByUserId(uuid);

        // 게시물이 존재하는지 확인
        assertTrue(postToUpdateStatus.isPresent());

        // 상태 변경
        postToUpdateStatus.ifPresent(post -> {
            // 게시물의 좋아요 개수가 100개 이상인 경우 상태 변경
            if (post.getLikePost() >= 100) {
                //post.setStatusPost("PREPRODUCT");
                postService.updatePostStatus(post);
            }
        });
    }

    @Test
    public void 상태변경및투표생성() throws Exception{
        for(int i=0; i<11; i++){
            List<Post> list = postService.selectAll();
            Post post = list.get(4);
            UUID id = post.getId();
            if(post.getLikePost()>=10) postService.updateStatus(post, StPost.PREPRODUCT);
            postService.addLike(id);

            System.out.println(i + "번쨰 시도) " + id + " 게시물의 좋아요 개수 : " + post.getLikePost() + ", 상태 = " + post.getStatusPost());
        }

        List<Post> list = postService.selectAll();
        Post post = list.get(4);
        Vote vote = voteService.selectVoteByPostId(post.getId());
        if(vote!=null){
            System.out.println(vote.getPost().getId() + " 글에 생성된 투표의 정보 : " + vote.getStatus() + ", " + vote.getVoteStart() + ", " + vote.getVoteEnd() + ", " + vote.getId());

        } else {
            System.out.println("투표가 생성되지 않았습니다.");
        }
    }
}


