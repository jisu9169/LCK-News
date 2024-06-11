package com.sparta.lck_news.security;

import static com.sparta.lck_news.entity.UserStatus.DEACTIVATED;

import com.sparta.lck_news.entity.User;
import com.sparta.lck_news.repository.UserRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

        if(Objects.equals(DEACTIVATED, user.getStatus())) {
            throw new IllegalArgumentException("계정이 비활성 상태 입니다.");
        }
        return new UserDetailsImpl(user);
    }
}