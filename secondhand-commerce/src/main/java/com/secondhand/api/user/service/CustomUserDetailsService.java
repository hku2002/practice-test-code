package com.secondhand.api.user.service;

import com.secondhand.api.user.domain.User;
import com.secondhand.api.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.secondhand.api.user.domain.enumtype.UserStatus.WITHDRAWAL;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) throw new UsernameNotFoundException("사용자 정보가 존재하지 않습니다.");
        return grantUser(user.get());
    }

    private org.springframework.security.core.userdetails.User grantUser(User user) {
        if (WITHDRAWAL.equals(user.getStatus())) throw new IllegalStateException("탈퇴한 유저입니다.");
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("DEFAULT");
        return new org.springframework.security.core.userdetails.User(
                user.getEmail()
                , user.getPassword()
                , List.of(grantedAuthority));
    }
}
