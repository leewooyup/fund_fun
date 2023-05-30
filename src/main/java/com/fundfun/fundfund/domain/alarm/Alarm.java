package com.fundfun.fundfund.domain.alarm;

import com.fundfun.fundfund.domain.user.Users;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String content;
    private boolean is_read;

    public static Alarm createAlarm(Users user, String content) {
        Alarm alarm = Alarm.builder()
                .user(user)
                .content(content)
                .is_read(false)
                .build();

        user.addAlarm(alarm);
        return alarm;
    }

    public void read() {
        this.is_read = true;
    }

}
