package com.fundfun.fundfund.controller.post;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PostForm {
    @NotEmpty(message = "제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요.")
    @Size(min = 10, max = 255, message = "게시물의 길이 제한을 초과했습니다.")
    private String contentPost;
}


