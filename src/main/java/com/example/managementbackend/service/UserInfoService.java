package com.example.managementbackend.service;

import com.example.managementbackend.entity.UserInfo;

public interface UserInfoService {

    UserInfo userRegister(UserInfo userInfoDTO);

    UserInfo userlogin(String email, String password);

}