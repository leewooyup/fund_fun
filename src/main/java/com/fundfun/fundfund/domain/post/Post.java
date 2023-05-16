package com.fundfun.fundfund.domain.post;

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
public class Post {

    @Id
    @GeneratedValue
    @Column(name="post_id")
    private UUID id;
    private String title;
    private String contentPost;
    private int likePost;

    private Date postDate;
    private Date updateDate;
    private String categoryPost;
    private String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.contentPost = contentPost;
    }

}
