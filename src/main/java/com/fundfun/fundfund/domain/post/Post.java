package com.fundfun.fundfund.domain.post;

import com.fundfun.fundfund.domain.vote.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="post_id")
    private UUID id;
    private String title;
    private String content_post;
    private int likes;

//
//    @CreationTimestamp
//    private String postDate;
//
//    @UpdateTimestamp
//    private String updateDate;
    private String category_post;
    private String status;


}
