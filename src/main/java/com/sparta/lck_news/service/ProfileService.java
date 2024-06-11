package com.sparta.lck_news.service;

import static com.sparta.lck_news.entity.UserStatus.DEACTIVATED;

import com.sparta.lck_news.dto.DeactivateRequestDto;
import com.sparta.lck_news.dto.ProfileRequestDto;
import com.sparta.lck_news.dto.ProfileResponseDto;
import com.sparta.lck_news.entity.User;
import com.sparta.lck_news.entity.UserStatus;
import com.sparta.lck_news.exception.CommonException;
import com.sparta.lck_news.exception.ErrorStatus;
import com.sparta.lck_news.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private final UserService userService;

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

    userService.isValidPassword(requestDto.getPassword());
    validateCurrentPassword(requestDto.getPassword(), findUser.getPassword());
    if(requestDto.getChangeChecked()) {
      userService.isValidPassword(requestDto.getNewPassword());
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
        .orElseThrow(() ->  new CommonException(ErrorStatus.ID_NOT_FOUND));
  }

  private void validateUserStatus(UserStatus userStatus) {
    if(Objects.equals(DEACTIVATED, userStatus)) {
      throw new CommonException(ErrorStatus.ACCOUNT_DISABLED);
    }
  }

  private void validateCurrentPassword(String requestPassword, String findUserPassword ) {
    if (!passwordEncoder.matches(requestPassword, findUserPassword)) {
      throw new CommonException(ErrorStatus.PASSWORD_MISMATCH);
    }
  }

  private void validateNewPassword(String requestNewPassword, String findUserPassword) {
    if (passwordEncoder.matches(requestNewPassword, findUserPassword)) {
      throw new CommonException(ErrorStatus.PASSWORD_SAME_AS_CURRENT);
    }
  }
}
