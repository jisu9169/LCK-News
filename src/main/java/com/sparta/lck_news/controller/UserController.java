package com.sparta.lck_news.controller;

import com.sparta.lck_news.dto.SignupRequestDto;
import com.sparta.lck_news.security.UserDetailsImpl;
import com.sparta.lck_news.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        try {
            userService.signup(requestDto);
            return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            userService.logout(userDetails.getUsername());
            return new ResponseEntity<>("로그아웃 성공", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}