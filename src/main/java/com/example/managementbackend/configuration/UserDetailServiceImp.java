package com.example.managementbackend.configuration;

import com.example.managementbackend.entity.UserInfo;
import com.example.managementbackend.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo =userInfoRepository.getByUserEmail(username);
        if(userInfo == null){
         throw new UsernameNotFoundException("User not found");
        }
        CustomUserDetail customUserDetail=new CustomUserDetail(userInfo);

        return customUserDetail;
    }
}
