package com.example.managementbackend.registration;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface UserInfoService {

    UserInfo userRegister(UserInfo dto) throws MessagingException, UnsupportedEncodingException;

    UserInfo userLogin(LoginData loginData);

    UserInfo findByUserName(String email);

    void sendVerificationEmail(UserInfo userInfo, String siteURL) throws MessagingException, UnsupportedEncodingException;

    boolean verify(String verificationCode);;

    void updateResetPassword(String verificationCode, String email);

    UserInfo getByResetPassword(String verificationCode);

    void updatePassword(UserInfo userInfo, String password);
}