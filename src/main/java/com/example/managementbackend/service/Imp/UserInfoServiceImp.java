package com.example.managementbackend.service.Imp;


import com.example.managementbackend.entity.UserInfo;
import com.example.managementbackend.repository.UserInfoRepository;
import com.example.managementbackend.service.UserInfoService;
import com.example.managementbackend.web.DTO.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImp implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

//    @Autowired
//    public  static PasswordEncoder passwordEncoder;

    @Override
    public UserInfo save(UserInfoDTO registration) {
        UserInfo userInfo=new UserInfo();
        userInfo.setFirstName(registration.getFirstName());
        userInfo.setLastName(registration.getLastName());
        userInfo.setEmail(registration.getEmail());
        userInfo.setPassword(registration.getPassword());
//        userInfo.setPassword(passwordEncoder.encode(registration.getPassword()));
        return userInfoRepository.save(userInfo);
    }



    @Override
    public boolean findByEmail(String email) {
        return userInfoRepository.findByEmail(email)!= null ? true : false;
    }


//    private static void encodePassword(UserInfo userInfo){
//        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
//    }




}
