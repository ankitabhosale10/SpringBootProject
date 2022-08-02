package com.example.managementbackend.service.Imp;


import com.example.managementbackend.entity.UserInfo;
import com.example.managementbackend.repository.UserInfoRepository;
import com.example.managementbackend.service.UserInfoService;
import com.example.managementbackend.web.DTO.UserInfoDTO;
import javassist.NotFoundException;
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
    public Object userRegister(UserInfoDTO registration) {
        UserInfo userInfo=new UserInfo();
        userInfo.setFirstName(registration.getFirstName());
        userInfo.setLastName(registration.getLastName());
        userInfo.setEmail(registration.getEmail());
        userInfo.setPassword(registration.getPassword());
        return userInfoRepository.save(userInfo);
    }

    @Override
    public Object userlogin(String email) throws NotFoundException {
        UserInfo userInfo=new UserInfo();
        if (userInfo == null) {
            throw new NotFoundException("Invalid username or password.");
        }
        return userInfoRepository.findByEmail(email);
    }


//    private static void encodePassword(UserInfo userInfo){
//        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
//    }




}
