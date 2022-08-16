package com.example.managementbackend.registration;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface UserInfoService {

    void userRegister(UserInfo dto, String siteURL) throws MessagingException, UnsupportedEncodingException;

    UserInfo userLogin(LoginData loginData);

    void sendVerificationEmail(UserInfo userInfo, String siteURL) throws MessagingException, UnsupportedEncodingException;

    boolean verify(String verificationCode);;

//    UserInfo userVerificationCode(String authToken);
}