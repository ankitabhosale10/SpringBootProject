package com.example.managementbackend.service;

import com.example.managementbackend.entity.UserInfo;
import com.example.managementbackend.web.DTO.UserInfoDTO;

public interface UserInfoService {

    boolean findByEmail(String email);

    UserInfo save(UserInfoDTO registration);

}