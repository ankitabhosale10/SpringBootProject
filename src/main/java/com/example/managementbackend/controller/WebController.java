package com.example.managementbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

@Controller
public class WebController
{
    @GetMapping("/")
    public ModelAndView welcomePage() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("home");
        return mv;
    }

    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("registration");
        return mv;
    }

    @GetMapping("/user-login")
    public ModelAndView openForm(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }
    @GetMapping("/products")
    public ModelAndView productEnter() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("product");
        return mv;
    }

}