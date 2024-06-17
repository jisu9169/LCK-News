package com.sparta.lck_news.mvc;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.lck_news.config.WebSecurityConfig;
import com.sparta.lck_news.controller.PostController;
import com.sparta.lck_news.controller.ProfileController;
import com.sparta.lck_news.controller.UserController;
import com.sparta.lck_news.dto.DeactivateRequestDto;
import com.sparta.lck_news.dto.ProfileRequestDto;
import com.sparta.lck_news.dto.SignupRequestDto;
import com.sparta.lck_news.entity.User;
import com.sparta.lck_news.security.UserDetailsImpl;
import com.sparta.lck_news.service.PostService;
import com.sparta.lck_news.service.ProfileService;
import com.sparta.lck_news.service.UserService;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {PostController.class, ProfileController.class, UserController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = WebSecurityConfig.class
        )
    }
)
public class ControllerTest {

  private MockMvc mvc;

  private Principal mockPrincipal;

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  UserService userService;

  @MockBean
  ProfileService profileService;

  @MockBean
  PostService postService;

  @BeforeEach
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(springSecurity(new MockSpringSecurityFilter()))
        .build();
  }

  private void mockUserSetup() {
    // Mock 테스트 유져 생성
    String username = "Username12";
    String password = "@12Password";
    SignupRequestDto signupRequestDto = new SignupRequestDto();
    signupRequestDto.setUsername(username);
    signupRequestDto.setPassword(password);
    User testUser = new User(signupRequestDto, password);
    UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
    mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
  }

  @Test
  @DisplayName("회원 가입 요청 처리")
  void test1() throws Exception {
    // given
    MultiValueMap<String, String> signupRequestForm = new LinkedMultiValueMap<>();
    signupRequestForm.add("username", "Username123");
    signupRequestForm.add("password", "@12Password");

    // when - then
    mvc.perform(post("/api/users/signup")
            .params(signupRequestForm)
        )
        .andDo(print());
  }

  @Test
  @DisplayName("프로필 수정 처리")
  void test2() throws Exception {
    this.mockUserSetup();
    String introduction ="introduction";
    String name ="Username1233";
    String email ="email@eamil.com";
    String password = "@12Password";
    Boolean changeChecked =true;
    String newPassword = "@12Password1";
    ProfileRequestDto requestDto = new ProfileRequestDto(introduction,name,email,password,changeChecked,newPassword);
    String info = objectMapper.writeValueAsString(requestDto);

    mvc.perform(patch("/api/users/profile/edit")
            .content(info)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .principal(mockPrincipal)
    )
        .andExpect(status().isOk())
        .andDo(print());
  }


  @Test
  @DisplayName("회원 비활성화 처리")
  void test3() throws Exception {
    this.mockUserSetup();
    String password = "@12Password";

    DeactivateRequestDto requestDto = new DeactivateRequestDto(password);
    String info = objectMapper.writeValueAsString(requestDto);

    mvc.perform(patch("/api/users/profile/deactivate")
            .content(info)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .principal(mockPrincipal)
        )
        .andExpect(status().isOk())
        .andDo(print());
  }
}
