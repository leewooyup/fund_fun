package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.dto.vote.VoteDto;
import com.fundfun.fundfund.service.vote.VoteService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void 게시물_생성() throws Exception {
        /*or (int i = 0; i < 5; i++) {
           Post p = Post.builder()
                    .id(UUID.randomUUID()).contentPost(null).title("제목"+i)
                   .categoryPost("주식형").likePost(10).build();
           postService.createPost(modelMapper.map(p, PostDto.class));
        }*/
        Post p = Post.builder().id(UUID.randomUUID()).title("제목3").contentPost("게시물3")
                .categoryPost("주식형").likePost(15).build();
        postService.createPost(modelMapper.map(p, PostDto.class));
    }

    @Test
    public void 게시물조회() throws Exception {
        List<PostDto> list = postService.selectAll();
        for (PostDto p : list) {
            System.out.println("제목 : " + p.getTitle() + ", 좋아요 수 : " + p.getLikePost() + ", 상태 : " + p.getStatusPost());
        }
    }

    @Test
    public void 제목_게시물조회() throws Exception {
        List<PostDto> list = postService.selectPostByKeyword("이거");
        for (PostDto p : list) System.out.println(p);
    }

    // @Test
   // public void 작성자_게시물조회() throws Exception {
    //    List<PostDto> olist = postService.selectPostByUserId(UUID.randomUUID());
    //    olist.ifPresent(post -> System.out.println(post));
   // }

    @Test
    public void 상태_게시물조회() throws Exception {
        List<PostDto> list = postService.selectPostByStatus(StPost.EARLY_IDEA);
        for (PostDto p : list) System.out.println(p);
    }

    @Test
    public void 카테고리_게시물조회() throws Exception {
        List<PostDto> list = postService.selectPostByCategory(null);
        for (PostDto p : list) System.out.println(p);
    }

    @Test
    public void 게시물삭제() throws Exception {
        UUID uuid = postService.selectAll().get(0).getId();
        PostDto list = postService.selectPostById(uuid);
        Post postToDelete = modelMapper.map(list, Post.class);

        //게시물이 존재하는지 확인
//        assertTrue(postToDelete.isPresent());
//
//        //게시물 삭제
//        postToDelete.ifPresent(post -> {
//            postService.deletePost(post);
//            assertFalse(postService.selectPostByUserId(uuid).isPresent());
//        });
    }

    @Test
    public void 게시물수정() throws Exception {

//        UUID uuid = postService.selectAll().get(0).getId();
//        // 수정할 게시물 가져오기 (수정할 게시물의 ID 또는 다른 고유 식별자 사용)
//        Optional<PostDto> postToUpdate = postService.selectPostByUserId(uuid);

        // 게시물이 존재하는지 확인
//        assertTrue(postToUpdate.isPresent());
//
//        // 게시물 수정
//        postToUpdate.ifPresent(post -> {
//            // 필드값 업데이트
//            post.setTitle("New Title");
//            post.setContentPost("New Content");
//
//            // 게시물 업데이트
//            postService.updatePost(post);
//
//            // 수정된 게시물 조회 및 확인
//            Optional<PostDto> updatedPost = postService.selectPostByUserId(uuid);
//            assertTrue(updatedPost.isPresent());
//            assertEquals("New Title", updatedPost.get().getTitle());
//            assertEquals("New Content", updatedPost.get().getContentPost());
//        });
    }

    @Test
    public void 좋아요정렬() throws Exception {
        List<PostDto> list = postService.getPostsOrderByLikes();
        for (PostDto p : list) System.out.println(p.getTitle() + " : " + p.getLikePost());
    }

    @Test
    public void 상태변경() throws Exception {

//        UUID uuid = postService.selectAll().get(0).getId();
        // 상태 변경할 게시물 가져오기 (게시물의 ID 또는 다른 고유 식별자 사용)
//        Optional<PostDto> postToUpdateStatus = postService.selectPostByUserId(uuid);

        // 게시물이 존재하는지 확인
//        assertTrue(postToUpdateStatus.isPresent());

        // 상태 변경
//        postToUpdateStatus.ifPresent(post -> {
//            // 게시물의 좋아요 개수가 100개 이상인 경우 상태 변경
//            if (post.getLikePost() >= 100) {
//                // post.setStatusPost("PREPRODUCT");
//                postService.updatePostStatus(post);
//            }
//        });
    }


    @Test
    public void 상태변경및투표생성() throws Exception{
        for(int i=0; i<11; i++){
            List<PostDto> list = postService.selectAll();
            PostDto postDto = list.get(0);
            //Post post = modelMapper.map(postDto, Post.class);
            if(postDto.getLikePost()>=5)
                postService.updateStatus(postDto, StPost.PREPRODUCT);
            postService.addLike(postDto.getId());

//            System.out.println(i + "번쨰 시도) " + id + " 게시물의 좋아요 개수 : " + post.getLikePost() + ", 상태 = " + post.getStatusPost());
        }

//        List<PostDto> list = postService.selectAll();
//        PostDto post = list.get(0);
//        VoteDto vote = voteService.selectVoteByPostId(post.getId());
//        if(vote!=null){
//            System.out.println(vote.getPost().getId() + " 글에 생성된 투표의 정보 : " + vote.getStatus() + ", " + vote.getVoteStart() + ", " + vote.getVoteEnd() + ", " + vote.getId());
//
//        } else {
//            System.out.println("투표가 생성되지 않았습니다.");
//        }
    }
}


