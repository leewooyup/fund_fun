package com.fundfun.fundfund.dto.post;

import com.fundfun.fundfund.domain.post.StPost;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.domain.vote.Vote;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class PostDto {
    private UUID id;
    private LocalDateTime createdAt;

    // 가독성 위해 파싱
    private String writeTime = null;

    private LocalDateTime updatedAt;
    @NotEmpty(message="제목을 입력해주세요.")
    private String title;
    @NotEmpty(message="내용을 입력해주세요.")
    @Size(min=10, max=255, message="게시물의 길이 제한을 초과했습니다.")
    private String contentPost;
    private int likePost;
    private String categoryPost;
    private StPost statusPost;
    private Vote vote;
    private Users user;

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }

    public void setCategoryPost(String categoryPost) {this.categoryPost = categoryPost;}
}
