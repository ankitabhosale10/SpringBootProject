package com.example.managementbackend.web;

import com.example.managementbackend.entity.UserInfo;
import com.example.managementbackend.repository.UserInfoRepository;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/")
public class RegistrationController {

    @Autowired
    private UserInfoRepository repo;


    @PostMapping("/register")
    public ModelAndView signup(@ModelAttribute UserInfo dto)
    {
        dto=repo.save(dto);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("welcome");
        mv.addObject("message","Thanks For Registration");
        mv.addObject("result",dto);
        return mv;
    }
}
