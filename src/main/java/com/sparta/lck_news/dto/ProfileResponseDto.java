package com.sparta.lck_news.dto;

import com.sparta.lck_news.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {

  private String username;
  private String introduction;
  private String name;
  private String email;

  public ProfileResponseDto(User user) {
    this.username = user.getUsername();
    this.name = user.getName();
    this.email = user.getEmail();
    this.introduction = user.getIntro();
  }
}
