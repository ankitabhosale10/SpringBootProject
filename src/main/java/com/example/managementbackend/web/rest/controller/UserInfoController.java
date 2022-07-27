package com.example.managementbackend.web.rest.controller;


import com.example.managementbackend.entity.UserInfo;
import com.example.managementbackend.service.UserInfoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserInfoController {

    private UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) { this.userInfoService = userInfoService; }

    @RequestMapping(value = "registration",method = RequestMethod.GET)
    public ModelAndView registrationPage(@ModelAttribute UserInfo userInfo){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userInfo",userInfo);
        modelAndView.setViewName("registration");
        return modelAndView;
    }


}
