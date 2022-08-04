package com.example.managementbackend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebConfiguration {

    @GetMapping("/")
    public ModelAndView welcomePage() {
       ModelAndView mv=new ModelAndView();
       mv.setViewName("index");
       return mv;
    }

    @GetMapping("/signup")
    public ModelAndView registration() {
        ModelAndView mv=new ModelAndView();
        mv.setViewName("registration");
        return mv;
    }
}
