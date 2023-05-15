package com.fundfun.fundfund.domain.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
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
    @Column(columnDefinition = "BINARY(16)", name="post_id")
    private UUID id;
    private String title;
    private String content;
    private int like;


    @CreationTimestamp
    private String postDate;

    @UpdateTimestamp
    private String updateDate;
    private String category;
    private String status;
}
