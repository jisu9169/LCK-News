package com.sparta.lck_news.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileRequestDto {

  private String introduction;
  private String name;
  private String email;
  private String password;    // 현재 비밀번호
  private Boolean changeChecked;
  private String newPassword;    // 변경할 비밀번호
}

