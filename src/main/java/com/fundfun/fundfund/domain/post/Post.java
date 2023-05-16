package com.fundfun.fundfund.domain.post;

import com.fundfun.fundfund.util.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name="post_id")
    private UUID id;
    private String title;
    private String contentPost;
    private int likePost;

    private String categoryPost;
    private String statusPost;

    public void setStatusPost(String statusPost) {
        this.statusPost = statusPost;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContentPost(String contentPost) {
        this.contentPost = contentPost;
    }

}

