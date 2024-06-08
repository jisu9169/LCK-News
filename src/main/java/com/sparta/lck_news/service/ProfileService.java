package com.sparta.lck_news.service;

import com.sparta.lck_news.dto.ProfileRequestDto;
import com.sparta.lck_news.dto.ProfileResponseDto;
import com.sparta.lck_news.entity.User;
import com.sparta.lck_news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public ProfileResponseDto getProfile(User user) {
    User findUser = findUserByUsername(user.getUsername());
    return new ProfileResponseDto(findUser);
  }

  @Transactional
  public ProfileResponseDto editProfile(ProfileRequestDto requestDto, User user) {
    User findUser = findUserByUsername(user.getUsername());
    validatePassword(requestDto.getPassword(), findUser.getPassword(), requestDto.getNewPassword(),
        requestDto.getChangeChecked());
    String password = passwordEncoder.encode(requestDto.getNewPassword());
    findUser.update(requestDto, password, requestDto.getChangeChecked());
    return new ProfileResponseDto(findUser);
  }

  private User findUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  private void validatePassword(String requestPassword, String findUserPassword,
      String requestNewPassword, Boolean isPasswordChanged) {
    if (!passwordEncoder.matches(requestPassword,findUserPassword)) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    if (isPasswordChanged && passwordEncoder.matches(requestNewPassword, findUserPassword)) {
      throw new IllegalArgumentException("변경할 비밀번호가 현재 비밀번호와 같습니다.");
    }
  }
}
