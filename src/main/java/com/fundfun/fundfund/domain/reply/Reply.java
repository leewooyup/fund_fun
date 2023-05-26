package com.fundfun.fundfund.domain.reply;

import com.fundfun.fundfund.domain.post.Post;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.util.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "reply")
public class Reply extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="reply_id")
    private UUID id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name="user_id")
    private Users user;

    private String contentReply;

    public void setContentReply(String contentReply){ this.contentReply = contentReply;}

    public void linkPost(Post post) {this.post = post;}

    public void linkUser(Users user) {this.user = user;}
}
