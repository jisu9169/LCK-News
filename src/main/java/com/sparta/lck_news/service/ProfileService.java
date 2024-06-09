package com.sparta.lck_news.service;

import com.sparta.lck_news.dto.DeactivateRequestDto;
import com.sparta.lck_news.dto.ProfileRequestDto;
import com.sparta.lck_news.dto.ProfileResponseDto;
import com.sparta.lck_news.entity.User;
import static com.sparta.lck_news.entity.UserStatus.*;

import com.sparta.lck_news.entity.UserStatus;
import com.sparta.lck_news.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional(readOnly = true)
  public ProfileResponseDto getProfile(User user) {
    User findUser = findUserByUsername(user.getUsername());
    validateUserStatus(findUser.getStatus());
    return new ProfileResponseDto(findUser);
  }

  @Transactional
  public ProfileResponseDto editProfile(ProfileRequestDto requestDto, User user) {
    User findUser = findUserByUsername(user.getUsername());
    validateUserStatus(findUser.getStatus());
    validateCurrentPassword(requestDto.getPassword(), findUser.getPassword());
    if(requestDto.getChangeChecked()) {
      validateNewPassword(requestDto.getNewPassword(), findUser.getPassword());
    }

    String password = passwordEncoder.encode(requestDto.getNewPassword());
    findUser.update(requestDto, password, requestDto.getChangeChecked());
    return new ProfileResponseDto(findUser);
  }

  @Transactional
  public void deactivateUser(DeactivateRequestDto requestDto, User user) {
    User findUser = findUserByUsername(user.getUsername());
    validateCurrentPassword(requestDto.getPassword(), findUser.getPassword());
    validateUserStatus(findUser.getStatus());
    findUser.update();
  }

  private User findUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
  }

  private void validateUserStatus(UserStatus userStatus) {
    if(Objects.equals(DEACTIVATED, userStatus)) {
      throw new IllegalArgumentException("계정이 비활성 상태 입니다.");
    }
  }

  private void validateCurrentPassword(String requestPassword, String findUserPassword ) {
    if (!passwordEncoder.matches(requestPassword, findUserPassword)) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }
  }

  private void validateNewPassword(String requestNewPassword, String findUserPassword) {
    if (passwordEncoder.matches(requestNewPassword, findUserPassword)) {
      throw new IllegalArgumentException("변경할 비밀번호가 현재 비밀번호와 같습니다.");
    }
  }
}
