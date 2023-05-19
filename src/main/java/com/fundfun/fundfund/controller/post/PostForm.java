package com.fundfun.fundfund.controller.post;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.domain.post.StPost;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

    @Getter
    @Setter
    public class PostForm {

    private UUID id;
    private String title;
    private String contentPost;
    private int likePost;
    private String categoryPost;

}


