package com.toy.project.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.toy.project.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        // ROLE_ 접두어 붙여서 권한 생성
        authorities.add(() -> "ROLE_" + userEntity.getRole());
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail(); // 보통 email을 username으로 사용
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 필요 시 로직 추가
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 필요 시 로직 추가
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 필요 시 로직 추가
    }

    @Override
    public boolean isEnabled() {
        return true; // 필요 시 로직 추가
    }
}