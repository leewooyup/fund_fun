package com.fundfun.fundfund.dto.post;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.domain.vote.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    private UUID id;
    private String title;
    private String contentPost;
    private int likePost;
    private String categoryPost;
    private StPost statusPost;
    private Vote vote;


    public void setStatusPost(StPost statusPost) {
        this.statusPost = statusPost;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }
    public void setLikePost(int likePost){ this.likePost = likePost; }

    public void linkVote(Vote vote) {
        this.vote = vote;
    }

}
