package com.sparta.lck_news.service;

import com.sparta.lck_news.dto.ProfileResponseDto;
import com.sparta.lck_news.entity.User;
import com.sparta.lck_news.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private final UserRepository userRepository;

  public ProfileResponseDto getProfile(User user) {
    User finduser = userRepository.findByUsername(user.getUsername()).orElseThrow(
        () -> new IllegalArgumentException("User not found")
    );

    return new ProfileResponseDto(finduser);
  }
}
