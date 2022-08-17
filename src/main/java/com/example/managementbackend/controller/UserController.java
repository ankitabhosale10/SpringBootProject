package com.example.managementbackend.controller;

import com.example.managementbackend.registration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInfoRepository repo;

    private BCryptPasswordEncoder passwordEncoder;
    private UserInfoService userInfoService;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/api/register")
    public ModelAndView signup(@ModelAttribute UserInfo userInfo, Model model, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        ModelAndView mv = new ModelAndView();
        userInfoService.userRegister(userInfo);
        String siteURL = Utility.getSiteURL(request);
        userInfoService.sendVerificationEmail(userInfo,siteURL);
            mv.setViewName("register_success");
            return mv;
    }

    @GetMapping("/verify")
    public ModelAndView verifyUser(@Param("code") String code,Model model) {
        ModelAndView mv = new ModelAndView();
        boolean verified= userInfoService.verify(code);
        if( verified ==true){
            mv.setViewName("verify_success");
            return mv;
        }
         mv.setViewName("verify_fail");
         return mv;
    }

    @PostMapping("/api/login")
    public ModelAndView processForm(@ModelAttribute LoginData loginData) {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userInfoService.userLogin(loginData);
        if (userInfo.getPassword().equals(loginData.getPassword())) {
            mv.setViewName("success");
            mv.addObject("message", "Thanks For Login");
            mv.addObject("loginData", loginData);
            return mv;
        }
        mv.setViewName("login");
        mv.addObject("message", "Invalid Credential");
        return mv;
    }

}
