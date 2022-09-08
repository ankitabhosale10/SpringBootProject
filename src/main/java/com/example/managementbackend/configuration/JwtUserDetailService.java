package com.example.managementbackend.configuration;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        if (email.equals(email)) {
            return new User("javainuse@gmail.com", "password", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

    }
}
