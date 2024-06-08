package com.sparta.lck_news.entity;

import com.sparta.lck_news.dto.ProfileRequestDto;
import com.sparta.lck_news.dto.SignupRequestDto;
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

//    @Column(name = "status", nullable = false)
//    private String status;

    @Column(name = "refresh_token")
    private String refreshToken;

//    @Column(name = "status_change_time")
//    private LocalDateTime statusChangeTime;




//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }

    public User(SignupRequestDto requestDto, String password) {
        this.username = requestDto.getUsername();
        this.password = password;
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.intro = requestDto.getIntro();
    }


    public void update(ProfileRequestDto requestDto, String newPassword, Boolean changePassword ){
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
        this.intro = requestDto.getIntroduction();

        if(changePassword){
            this.password = newPassword;
        }

    }
}






