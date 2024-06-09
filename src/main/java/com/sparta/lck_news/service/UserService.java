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
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        // 사용자 등록
        User user = new User(requestDto, password);
        userRepository.save(user);
    }

    @Transactional
    public void logout(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 사용자 정보입니다.")
        );

//        user.updateRefreshToken(null);
        userRepository.save(user);
    }
}