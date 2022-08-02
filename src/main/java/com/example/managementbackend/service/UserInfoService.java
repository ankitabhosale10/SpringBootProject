package com.example.managementbackend.service;

import com.example.managementbackend.entity.UserInfo;
import com.example.managementbackend.web.DTO.UserInfoDTO;
import javassist.NotFoundException;

public interface UserInfoService {
    Object userRegister(UserInfoDTO userInfoDTO);

    Object userlogin(String email) throws NotFoundException;

}