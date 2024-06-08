package com.sparta.lck_news.controller;

import com.sparta.lck_news.dto.ProfileResponseDto;
import com.sparta.lck_news.security.UserDetailsImpl;
import com.sparta.lck_news.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ProfileController {

  private final ProfileService profileService;

  @GetMapping("/profile")
  public ResponseEntity<ProfileResponseDto> getProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ProfileResponseDto responseDto = profileService.getProfile(userDetails.getUser());
    return ResponseEntity.ok(responseDto);
  }



}
