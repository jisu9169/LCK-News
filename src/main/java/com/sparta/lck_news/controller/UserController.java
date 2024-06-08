package com.sparta.lck_news.Controller;

import com.sparta.lck_news.dto.SignupRequestDto;
import com.sparta.lck_news.security.UserDetailsImpl;
import com.sparta.lck_news.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }

//    @GetMapping("/user/signup")
//    public String signupPage() {
//        return "signup";
//    }

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

}