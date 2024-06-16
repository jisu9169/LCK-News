package com.sparta.lck_news;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.sparta.lck_news.dto.DeactivateRequestDto;
import com.sparta.lck_news.dto.LoginRequestDto;
import com.sparta.lck_news.dto.PostCreateRequest;
import com.sparta.lck_news.dto.PostListResponse;
import com.sparta.lck_news.dto.PostResponse;
import com.sparta.lck_news.dto.ProfileRequestDto;
import com.sparta.lck_news.dto.ProfileResponseDto;
import com.sparta.lck_news.dto.SignupRequestDto;
import com.sparta.lck_news.dto.TokenDto;
import com.sparta.lck_news.entity.Post;
import com.sparta.lck_news.entity.User;
import com.sparta.lck_news.entity.UserStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DtoAndEntityTest {

  Long id = 1L;
  String username = "username";
  String password = "password";
  String content = "content";
  String name = "name";
  String introduction = "introduction";
  String email = "email";
  Boolean changeChecked = false;
  String newPassword = "newPassword";
  LocalDateTime dateTime = LocalDateTime.now();
  String grantType = "grantType";
  String accessToken = "accessToken";
  String refreshToken = "refreshToken";

  @Test
  @DisplayName("deactivateRequestDto 테스트")
  void dtoTest1() {
    DeactivateRequestDto deactivateRequestDto = new DeactivateRequestDto(password);
    assertEquals(deactivateRequestDto.getPassword(), password);
  }

  @Test
  @DisplayName("loginRequestDto 테스트")
  void dtoTest2() {
    LoginRequestDto loginRequestDto = new LoginRequestDto();
    loginRequestDto.setUsername(username);
    loginRequestDto.setPassword(password);
    assertEquals(loginRequestDto.getPassword(), password);
    assertEquals(loginRequestDto.getUsername(), username);
  }

  @Test
  @DisplayName("preCreateRequest 테스트")
  void dtoTest3() {
    PostCreateRequest preCreateRequest = new PostCreateRequest();
    preCreateRequest.setContent(content);
    assertEquals(preCreateRequest.getContent(), content);
  }

  @Test
  @DisplayName("postListResponse 테스트")
  void dtoTest4() {
    PostListResponse postListResponse = new PostListResponse(name, content, dateTime, dateTime);
    assertEquals(postListResponse.getContent(), content);
    assertEquals(postListResponse.getName(), name);
    assertEquals(postListResponse.getCreatedAt(), dateTime);
    assertEquals(postListResponse.getUpdatedAt(), dateTime);
  }

  @Test
  @DisplayName("postResponse 테스트")
  void dtoTest5() {
    PostResponse postResponse = new PostResponse(id, content, dateTime, dateTime);
    assertEquals(postResponse.getContent(), content);
    assertEquals(postResponse.getId(), id);
    assertEquals(postResponse.getCreatedAt(), dateTime);
    assertEquals(postResponse.getUpdatedAt(), dateTime);
  }

  @Test
  @DisplayName("profileRequestDto 테스트")
  void dtoTest6() {
    ProfileRequestDto profileRequestDto = new ProfileRequestDto(introduction, name, email, password,
        changeChecked, newPassword);
    assertEquals(profileRequestDto.getIntroduction(), introduction);
    assertEquals(profileRequestDto.getName(), name);
    assertEquals(profileRequestDto.getEmail(), email);
    assertEquals(profileRequestDto.getChangeChecked(), changeChecked);
    assertEquals(profileRequestDto.getNewPassword(), newPassword);
  }

  @Test
  @DisplayName("profileResponseDto 테스트")
  void dtoTest7() {
    ProfileResponseDto profileResponseDto = new ProfileResponseDto(username, introduction, name,
        email);
    assertEquals(profileResponseDto.getUsername(), username);
    assertEquals(profileResponseDto.getIntroduction(), introduction);
    assertEquals(profileResponseDto.getName(), name);
    assertEquals(profileResponseDto.getEmail(), email);
  }
  @Test
  @DisplayName("signupRequestDto 테스트")
  void dtoTest8() {
    SignupRequestDto signupRequestDto = new SignupRequestDto();
    signupRequestDto.setUsername(username);
    signupRequestDto.setPassword(password);
    assertEquals(signupRequestDto.getUsername(), username);
    assertEquals(signupRequestDto.getPassword(), password);
  }
  @Test
  @DisplayName("tokenDto 테스트")
  void dtoTest9() {
    TokenDto tokenDto = new TokenDto(grantType, accessToken, refreshToken);
    assertEquals(tokenDto.getGrantType(), grantType);
    assertEquals(tokenDto.getAccessToken(), accessToken);
    assertEquals(tokenDto.getRefreshToken(), refreshToken);
  }

  @Test
  @DisplayName("post 엔티티 테스트")
  void entityTest1(){
    Post post = new Post();
    post.setId(id);
    post.setContent(content);

    assertEquals(post.getId(), id);
    assertEquals(post.getContent(), content);
  }

  @Test
  @DisplayName("user 엔티티 테스트")
  void entityTest2(){
    UserStatus active = UserStatus.ACTIVE;
    User user = new User();
    user.setId(id);
    user.setUsername(username);
    user.setPassword(password);
    user.setEmail(email);
    user.setName(name);
    user.setIntro(introduction);
    user.setStatus(active);
    user.setRefreshToken(refreshToken);

    assertEquals(user.getId(), id);
    assertEquals(user.getUsername(), username);
    assertEquals(user.getIntro(), introduction);
    assertEquals(user.getRefreshToken(), refreshToken);
    assertEquals(user.getPassword(), password);
    assertEquals(user.getEmail(), email);
    assertEquals(user.getStatus(), active);
    assertEquals(user.getName(), name);
  }
}
