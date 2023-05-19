package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.repository.post.PostRepository;
import com.fundfun.fundfund.repository.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final VoteRepository voteRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public Post createPost(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return postRepository.save(post);
    }

    @Override
    public List<PostDto> selectAll() {
        return postRepository.findAll().stream()
                .map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }


    @Override
    public List<PostDto> selectPostByUserId(UUID userId) {
//        return modelMapper.map(postRepository.findById(userId).
//                orElse(null), PostDto.class);
        return postRepository.findById(userId).stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    };


    @Override
    public List<PostDto> selectPostByKeyword(String title) {
//      return modelMapper.map(postRepository.findBytitle(title).
//              orElse(null), PostDto.class);
        return postRepository.findByTitleContaining(title).stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

    }

    @Override
    public List<PostDto> selectPostByStatus(StPost status) {
        return postRepository.findByStatusPost(status).stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> selectPostByCategory(String category) {
        return postRepository.findByCategoryPost(category).stream().map(post -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }



    @Override
    public void deletePost(UUID postId){
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null)
            throw new RuntimeException("해당 게시물이 존재하지 않습니다.");
        postRepository.delete(post);
    }

    @Override
    public void updatePost(UUID postId, String newTitle, String newContent) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            throw new RuntimeException("해당 게시물이 존재하지 않습니다");
        }

        // 게시물 업데이트 로직을 추가합니다.
        post.setTitle(newTitle);       // 제목 수정
        post.setContentPost(newContent);   // 내용 수정

        postRepository.save(post);
    }



    @Override
    public List<PostDto> getPostsOrderByLikes() {
        List<Post> posts = postRepository.findAll(Sort.by(Sort.Direction.DESC, "likePost"));
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addLike(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다."));

        post.setLikePost(post.getLikePost() + 1); // 좋아요 수 증가
        postRepository.save(post);

    }

    @Override
    public void updateStatus(PostDto postDto, StPost status) {
        Post post = modelMapper.map(postDto, Post.class);

        post.setStatusPost(status);
        Vote vote = new Vote();
        vote.linkPost(post);
        voteRepository.save(vote);
        post.linkVote(vote);
        postRepository.save(post);
    }


    @Override
    public int getLikeById(UUID postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다."));
        return post.getLikePost();
    }



}