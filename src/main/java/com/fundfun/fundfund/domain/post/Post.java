package com.fundfun.fundfund.domain.post;

import com.fundfun.fundfund.domain.portfolio.Portfolio;
import com.fundfun.fundfund.util.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private UUID id;
    private String title;
    private String contentPost;
    private int likePost;

    private Date postDate;
    private Date updateDate;
    private String categoryPost;

    @ColumnDefault("EARLY_IDEA")
    private String statusPost;



    @OneToMany(mappedBy = "post")
    private List<Portfolio> portfolios = new ArrayList<>();

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


