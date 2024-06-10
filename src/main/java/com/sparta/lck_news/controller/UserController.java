package com.sparta.lck_news.controller;

import com.sparta.lck_news.dto.SignupRequestDto;
import com.sparta.lck_news.security.UserDetailsImpl;
import com.sparta.lck_news.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
    userService.signup(requestDto);
    return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    userService.logout(userDetails.getUsername());
    return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);
  }
}