package com.example.managementbackend.controller;

import com.example.managementbackend.registration.LoginData;
import com.example.managementbackend.registration.UserInfo;
import com.example.managementbackend.registration.UserInfoRepository;
import com.example.managementbackend.registration.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.UUID;

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
    public ModelAndView signup( @ModelAttribute UserInfo dto) {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userInfoService.userRegister(dto);
        if(userInfo != null) {
            mv.setViewName("welcome");
            mv.addObject("message", "Thanks For Registration");
            mv.addObject("result", dto);
            return mv;
        } else {
            mv.setViewName("registration");
            mv.addObject("message", "email already exist");
            return mv;
        }
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

    @GetMapping("/account-verification/{authToken}")
    public ModelAndView checkToken(@RequestParam("authToken") String authToken) {
        ModelAndView mv = new ModelAndView();
        UserInfo dto = userInfoService.userVerificationCode(authToken);
        if (dto != null) {
            mv.setViewName("verification-success");
            mv.addObject("message", "Your Account is verified");
            return mv;
        }
        mv.setViewName("verification-failed");
        mv.addObject("message", "Verification link is expired");
        return mv;
    }
}
