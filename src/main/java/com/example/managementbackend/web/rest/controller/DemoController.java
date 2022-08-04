package com.example.managementbackend.web.rest.controller;

import com.example.managementbackend.entity.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

@Controller
public class DemoController
{
    @RequestMapping("/")
    public String index()
    {
        return"index";
    }
    @RequestMapping(value="/save", method=RequestMethod.GET)
    public ModelAndView save(@ModelAttribute ("userInfo") UserInfo userInfo)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject("userInfo", userInfo);
        return modelAndView;
    }

    @GetMapping("/loginForm")
    public ModelAndView openForm(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("loginForm");
        return modelAndView;
    }

}