package com.sparta.lck_news.controller;

import com.sparta.lck_news.dto.SignupRequestDto;
import com.sparta.lck_news.security.UserDetailsImpl;
import com.sparta.lck_news.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/user/signup")
    public String signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
        }

        try {
            userService.signup(requestDto);
        } catch (IllegalArgumentException e) {
            return "회원가입 실패";

        }
        return "회원가입완료";
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            userService.logout(userDetails.getUsername());
            return ResponseEntity.ok("로그아웃 성공");
        } else {
            return ResponseEntity.badRequest().body("로그아웃 실패: 사용자 정보를 찾을 수 없습니다.");
        }
    }

}