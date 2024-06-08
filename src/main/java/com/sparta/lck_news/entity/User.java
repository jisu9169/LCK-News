package com.sparta.lck_news.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 20)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "intro", length = 255)
    private String intro;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "status_change_time")
    private LocalDateTime statusChangeTime;




//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
//        this.email = email;
//        this.name = name;
//        this.status = status;
//        this.intro = intro;
//        this.refreshToken = refreshToken;
//        this.statusChangeTime = statusChangeTime;
    }


}






