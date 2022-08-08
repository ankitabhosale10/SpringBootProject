package com.example.managementbackend.configuration;


import com.example.managementbackend.registration.UserInfo;
import com.example.managementbackend.registration.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo =userInfoRepository.getByUserEmail(username);
        if(userInfo == null){
         throw new UsernameNotFoundException(USER_NOT_FOUND_MSG);
        }
        CustomUserDetail customUserDetail=new CustomUserDetail(userInfo);
        return customUserDetail;
    }
}
