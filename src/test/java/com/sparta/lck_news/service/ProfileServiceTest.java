package com.sparta.lck_news.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.sparta.lck_news.dto.ProfileRequestDto;
import com.sparta.lck_news.dto.ProfileResponseDto;
import com.sparta.lck_news.dto.SignupRequestDto;
import com.sparta.lck_news.entity.User;
import com.sparta.lck_news.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProfileServiceTest {

  @Autowired
  ProfileService profileService;
  @Autowired
  UserRepository  userRepository;
  @Autowired
  PasswordEncoder passwordEncoder;

  User user;

  String username;
  String password;
  String email;
  String introduction;
  String name;

  @BeforeEach
  void setUp() {
    username = "Username12";
    password = "@12Password";
    email = "email@email.com";
    introduction = "Introduction";
    name = "name";

    user = userRepository.findByUsername(username).orElse(null);

  }


  @Test
  @Order(1)
  @DisplayName("프로필 조회")
  void test1() {
    ProfileResponseDto profileResponseDto = profileService.getProfile(user);
    assertEquals(username, profileResponseDto.getUsername());
    assertEquals("not update profile", profileResponseDto.getIntroduction());
    assertEquals("not update profile", profileResponseDto.getName());
    assertEquals("Username12", profileResponseDto.getEmail());
  }

  @Test
  @Order(1)
  @DisplayName("프로필 수정")
  void test2() {

    Boolean changeChecked = false;
    String newPassword ="@12Password123";
    ProfileRequestDto requestDto = new ProfileRequestDto(introduction, name, email, password, changeChecked, newPassword);
    profileService.editProfile(requestDto,user);

    assertEquals(introduction, requestDto.getIntroduction());
    assertEquals(email, requestDto.getEmail());
    assertEquals(name, requestDto.getName());
    assertNotEquals(newPassword, requestDto.getPassword());
  }

}
