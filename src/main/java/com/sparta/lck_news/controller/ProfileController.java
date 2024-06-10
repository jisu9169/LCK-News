package com.sparta.lck_news.controller;

import com.sparta.lck_news.dto.DeactivateRequestDto;
import com.sparta.lck_news.dto.ProfileRequestDto;
import com.sparta.lck_news.dto.ProfileResponseDto;
import com.sparta.lck_news.security.UserDetailsImpl;
import com.sparta.lck_news.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/profile")
public class ProfileController {
  private final ProfileService profileService;

  @GetMapping
  public ResponseEntity<ProfileResponseDto> getProfile(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ProfileResponseDto responseDto = profileService.getProfile(userDetails.getUser());
    return ResponseEntity.ok(responseDto);
  }

  @PatchMapping("/edit")
  public ResponseEntity<ProfileResponseDto> editProfile(@RequestBody ProfileRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    ProfileResponseDto responseDto = profileService.editProfile(requestDto, userDetails.getUser());
    return ResponseEntity.ok(responseDto);
  }

  @PatchMapping("/deactivate")
  public ResponseEntity<Void> deactivateUser(@RequestBody DeactivateRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    profileService.deactivateUser(requestDto, userDetails.getUser());
    return ResponseEntity.ok().build();
  }

}
