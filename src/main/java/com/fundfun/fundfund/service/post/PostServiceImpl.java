package com.fundfun.fundfund.service.post;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.Vote;
import com.fundfun.fundfund.dto.portfolio.PortfolioDto;
import com.fundfun.fundfund.dto.post.PostDto;
import com.fundfun.fundfund.repository.post.PostRepository;
import com.fundfun.fundfund.repository.user.UserRepository;
import com.fundfun.fundfund.repository.vote.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final UserRepository userRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Override
    public Post createPost(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return postRepository.save(post);
    }

    @Override
    public List<PostDto> selectAll() {
        List<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PostDto> postDtoList = new ArrayList<>();

        for(Post p : postList){
            PostDto postDto = modelMapper.map(p, PostDto.class);
            postDto.setWriteTime(p.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
            postDtoList.add(postDto);
        }

        return postDtoList;
    }

    @Override
    public Page<PostDto> selectAll(Pageable pageable) {
        Page<Post> postList = postRepository.findAll(pageable);

        Page<PostDto> postDtoList = postList.map( m -> PostDto.builder()
                .id(m.getId())
                .createdAt(m.getCreatedAt())
                .writeTime(m.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .updatedAt(m.getUpdatedAt())
                .title(m.getTitle())
                .contentPost(m.getContentPost())
                .likePost(m.getLikePost())
                .categoryPost(m.getCategoryPost())
                .statusPost(m.getStatusPost())
                .vote(m.getVote())
                .user(m.getUser())
                .build());

        return postDtoList;
    }

    @Override
    public PostDto selectPostById(UUID postId) {
        System.out.println("postId = " + postId);
        Post post = postRepository.findById(postId).orElse(null);
        System.out.println("post의 id = " + post.getId());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PostDto result = modelMapper.map(post, PostDto.class);
        result.setWriteTime(post.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        return result;
    };


    @Override
    public Page<PostDto> selectPostByKeyword(String title, Pageable pageable) {
        Page<Post> postList = postRepository.findByTitleContaining(title, pageable);
        Page<PostDto> postDtoList = postList.map( m -> PostDto.builder()
                .id(m.getId())
                .createdAt(m.getCreatedAt())
                .writeTime(m.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .updatedAt(m.getUpdatedAt())
                .title(m.getTitle())
                .contentPost(m.getContentPost())
                .likePost(m.getLikePost())
                .categoryPost(m.getCategoryPost())
                .statusPost(m.getStatusPost())
                .vote(m.getVote())
                .user(m.getUser())
                .build());

        return postDtoList;
    }

    @Override
    public Page<PostDto> selectPostByStatus(StPost status, Pageable pageable) {
        Page<Post> postList = postRepository.findByStatusPost(status, pageable);
        Page<PostDto> postDtoList = postList.map( m -> PostDto.builder()
                .id(m.getId())
                .createdAt(m.getCreatedAt())
                .writeTime(m.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .updatedAt(m.getUpdatedAt())
                .title(m.getTitle())
                .contentPost(m.getContentPost())
                .likePost(m.getLikePost())
                .categoryPost(m.getCategoryPost())
                .statusPost(m.getStatusPost())
                .vote(m.getVote())
                .user(m.getUser())
                .build());

        return postDtoList;
    }

    @Override
    public Page<PostDto> selectPostByCategory(String category, Pageable pageable) {
        Page<Post> postList = postRepository.findByCategoryPost(category, pageable);
        Page<PostDto> postDtoList = postList.map( m -> PostDto.builder()
                .id(m.getId())
                .createdAt(m.getCreatedAt())
                .writeTime(m.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .updatedAt(m.getUpdatedAt())
                .title(m.getTitle())
                .contentPost(m.getContentPost())
                .likePost(m.getLikePost())
                .categoryPost(m.getCategoryPost())
                .statusPost(m.getStatusPost())
                .vote(m.getVote())
                .user(m.getUser())
                .build());

        return postDtoList;
    }

    @Override
    public List<PostDto> selectPostByUserId(UUID userId){
        Users user = userRepository.findById(userId).orElse(null);
        if(user != null){
            List<Post> postList = postRepository.findByUser(user);
            List<PostDto> postDtoList = postList.stream().map(p -> modelMapper.map(p, PostDto.class)).collect(Collectors.toList());
            return postDtoList;
        } else {
            return null;
        }
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
    public Page<PostDto> getPostsOrderByLikes(Pageable pageable) {
        //Page<Post> postList = postRepository.findAll(Sort.by(Sort.Direction.DESC, "likePost"));
        Page<Post> postList = postRepository.findAll(pageable);
        Page<PostDto> postDtoList = postList.map( m -> PostDto.builder()
                .id(m.getId())
                .createdAt(m.getCreatedAt())
                .writeTime(m.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")))
                .updatedAt(m.getUpdatedAt())
                .title(m.getTitle())
                .contentPost(m.getContentPost())
                .likePost(m.getLikePost())
                .categoryPost(m.getCategoryPost())
                .statusPost(m.getStatusPost())
                .vote(m.getVote())
                .user(m.getUser())
                .build());

        return postDtoList;
    }

    @Override
    public void addLike(UUID postId, Users user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다."));

        post.setLikePost(post.getLikePost() + 1); // 좋아요 수 증가
        user.minusCount();
        userRepository.save(user);
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