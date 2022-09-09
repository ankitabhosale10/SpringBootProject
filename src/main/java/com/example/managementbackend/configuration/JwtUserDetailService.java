package com.example.managementbackend.configuration;


import com.example.managementbackend.registration.UserInfo;
import com.example.managementbackend.registration.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;


    //    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        if ("javaSpingBoot".equals(email)) {
//            return new User("javaSpingBoot", "password", new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + email);
//        }
//
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = userInfoRepository.getByUserEmail(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            CustomUserDetail customUserDetail = new CustomUserDetail(userInfo);
            return customUserDetail;
        }

    }
}
