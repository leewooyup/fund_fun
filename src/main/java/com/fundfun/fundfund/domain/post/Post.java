package com.fundfun.fundfund.domain.post;

import com.fundfun.fundfund.domain.user.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="post_id")
    private UUID id;
    private String title;
    private String content;
    private int like;

    @ManyToOne
    private Users writer;


    private String postDate;


    private String updateDate;
    private String category;
    private String status;
}
