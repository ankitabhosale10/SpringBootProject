package com.example.managementbackend.service.Imp;


import com.example.managementbackend.entity.UserInfo;
import com.example.managementbackend.repository.UserInfoRepository;
import com.example.managementbackend.service.UserInfoService;
import com.example.managementbackend.web.DTO.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserInfoServiceImp implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Object userRegister(UserInfoDTO registration) {
        UserInfo userInfo=new UserInfo();
        userInfo.setFirstName(registration.getFirstName());
        userInfo.setLastName(registration.getLastName());
        userInfo.setEmail(registration.getEmail());
        userInfo.setPassword(passwordEncoder.encode(registration.getPassword()));
        return userInfoRepository.save(userInfo);
    }

    @Override
    public Object userlogin(String email, String password)  {
        UserInfo userInfo=new UserInfo();
        if (userInfo == null) {
//            throw new NotFoundException("Invalid username or password.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(userInfoRepository.findByEmail(email)));

    }


}
