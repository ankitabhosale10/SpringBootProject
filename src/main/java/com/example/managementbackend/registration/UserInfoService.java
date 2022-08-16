package com.example.managementbackend.registration;

import java.util.Optional;

public interface UserInfoService {

    UserInfo userRegister(UserInfo dto);

    UserInfo userLogin(LoginData loginData);

    UserInfo userVerificationCode(String authToken);


}