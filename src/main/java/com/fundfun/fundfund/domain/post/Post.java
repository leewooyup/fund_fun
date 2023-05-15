package com.fundfun.fundfund.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private UUID postId;
    private String title;
    private String content;
    private String post_date;
    private int like;
    private String update_date;
    private String category;
    private String status;

}
