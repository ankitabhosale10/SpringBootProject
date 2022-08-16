package com.example.managementbackend.configuration;

import com.example.managementbackend.registration.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {

    private UserInfo userInfo;

    public CustomUserDetail(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Enumerated(EnumType.STRING)
    private CustomUserRole customUserRole;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userInfo.getEmail());
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() { return  userInfo.isActive(); }
}
