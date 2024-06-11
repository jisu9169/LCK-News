package com.sparta.lck_news.service;

import com.sparta.lck_news.dto.SignupRequestDto;
import com.sparta.lck_news.entity.User;
import com.sparta.lck_news.exception.CommonException;
import com.sparta.lck_news.exception.ErrorStatus;
import com.sparta.lck_news.jwt.JwtUtil;
import com.sparta.lck_news.repository.UserRepository;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
            throw new CommonException(ErrorStatus.DUPLICATE_USER);
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
                () -> new CommonException(ErrorStatus.ID_NOT_FOUND)
        );
        userRepository.save(user);
    }

    public void isValidUsername(String username) {
        // 대소문자 포함 영문 + 숫자, 10자 이상 20자 이하
        String regex = "^[a-zA-Z0-9]{10,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("대소문자 포함 영문 + 숫자, 10자 이상 20자 이하로 다시 입력해주세요");

        }
    }

    public void isValidPassword(String password) {
        // 대소문자 포함 영문 + 숫자 + 특수문자 최소 1글자씩 포함, 최소 10글자 이상
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*])[A-Za-z\\d!@#\\$%\\^&\\*]{10,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("대소문자 포함 영문 + 숫자 + 특수문자 최소 1글자씩 포함, 최소 10글자 이상으로 다시 입력해주세요");

        }
    }
}

