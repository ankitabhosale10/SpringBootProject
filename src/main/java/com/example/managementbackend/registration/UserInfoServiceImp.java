package com.example.managementbackend.registration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserInfoServiceImp implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public UserInfo userRegister(UserInfo dto) {
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(dto.getFirstName());
        userInfo.setLastName(dto.getLastName());
        userInfo.setEmail(dto.getEmail());
        userInfo.setPassword(passwordEncoder.encode(dto.getPassword()));
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(ts);
        dto.setActive(false);
        dto.setDeleted(false);
        UUID uuid = UUID.randomUUID();
        dto.setVerificationCode(uuid.toString());
        return userInfoRepository.save(dto);
    }
    @Override
    public UserInfo userLogin(LoginData loginData) {
        UserInfo userInfo = new UserInfo();
        if (loginData == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return userInfoRepository.findByEmail(loginData.getEmail());
    }


    @Override
    public UserInfo userVerificationCode(String authToken) {
        UserInfo dto=new UserInfo();
        dto.setActive(true);
        dto.setVerificationCode(null);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        dto.setModifiedDate(ts);
        return userInfoRepository.save(dto);
    }

}
