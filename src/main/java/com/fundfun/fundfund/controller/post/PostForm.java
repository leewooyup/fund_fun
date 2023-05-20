package com.fundfun.fundfund.controller.post;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.post.StPost;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

    @Getter
    @Setter
    public class PostForm {

    private UUID id;
    @NotEmpty(message="제목을 입력해주세요.")
    private String title;
    @NotEmpty(message="내용을 입력해주세요.")
    @Size(min=30, max=1000, message="게시물의 길이 제한을 초과했습니다.")
    private String contentPost;
    private int likePost;
    private String categoryPost;

}


