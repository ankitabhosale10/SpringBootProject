package com.example.managementbackend.registration;

import java.util.Optional;

public interface UserInfoService {

    UserInfo userRegister(UserInfo userInfoDTO);

    Optional<UserInfo> userlogin(String email, String password);

}