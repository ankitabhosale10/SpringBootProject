package com.example.managementbackend.service;

import com.example.managementbackend.web.DTO.UserInfoDTO;

public interface UserInfoService {

    Object userRegister(UserInfoDTO userInfoDTO);

    Object userlogin(String email, String password);

}