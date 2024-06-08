package com.sparta.lck_news.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {

  @NotBlank
  private String username;
  @NotBlank
  private String password;
  //    @Email
//    @NotBlank
//    private String email;
  private String name;
  private String email;
  private String intro;
}