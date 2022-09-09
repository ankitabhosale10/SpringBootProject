package com.example.managementbackend.controller;

import com.example.managementbackend.registration.UserInfo;
import com.example.managementbackend.registration.UserInfoRepository;
import com.example.managementbackend.registration.UserInfoService;
import com.example.managementbackend.registration.Utility;
import com.example.managementbackend.token.JwtRequest;
import com.example.managementbackend.token.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/postman/user")
public class PostmanUseController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping("/api/register")
    public String signup(@RequestBody UserInfo userInfo, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        UserInfo userInfo1 = userInfoService.findByUserName(userInfo.getEmail());
        if (userInfo1 != null) {
            return "already exist";
        }
        userInfoService.userRegister(userInfo);
        String siteURL = Utility.getSiteURL(request);
        userInfoService.sendVerificationEmail(userInfo, siteURL);

        return "Your account is registered successfully . Please check your email to active your account";
    }

    @PostMapping("/api/login")
    public String signin(@RequestBody JwtRequest userLogin, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {

        UserInfo userInfo = userInfoService.userLogin(userLogin);
        if (userInfo != null && (userInfo.getPassword().equals(userLogin.getPassword()) && userInfo.isActive())) {

            String token = jwtTokenUtil.generateToken(userLogin);

            userInfo.setAuthToken(token);
            userInfo = userInfoRepository.save(userInfo);


            return userInfo.toString();
        }

        return "invalid credentials";
    }
}
