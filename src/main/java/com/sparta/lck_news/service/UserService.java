package com.sparta.lck_news.service;

import com.sparta.lck_news.dto.LoginRequestDto;
import com.sparta.lck_news.dto.SignupRequestDto;
import com.sparta.lck_news.entity.User;
import com.sparta.lck_news.jwt.JwtUtil;
import com.sparta.lck_news.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }


    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 아이디 형식 확인 (대소문자 포함 영문 + 숫자, 10자 이상 20자 이하)
        isValidUsername(username);
        // 비밀번호 형식 확인 (대소문자 포함 영문 + 숫자 + 특수문자 최소 1글자씩 포함, 최소 10글자 이상)
        isValidPassword(password);

        // 비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(password);

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        // 사용자 등록
        User user = new User(requestDto, encodedPassword);
        user.setEmail(requestDto.getUsername());
        userRepository.save(user);
    }

    @Transactional
    public void logout(String username) {
        System.out.println(username);
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 사용자 정보입니다.")
        );
        userRepository.save(user);
    }

    public void isValidUsername(String username) {
        // 대소문자 포함 영문 + 숫자, 10자 이상 20자 이하
        String regex = "^[a-zA-Z0-9]{10,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("아이디 형식이 올바르지 않습니다.");
        }
    }

    public void isValidPassword(String password) {
        // 대소문자 포함 영문 + 숫자 + 특수문자 최소 1글자씩 포함, 최소 10글자 이상
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*])[A-Za-z\\d!@#\\$%\\^&\\*]{10,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
        }
    }
}

