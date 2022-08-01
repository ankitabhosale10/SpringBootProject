package com.example.managementbackend.web.rest.controller;

import com.example.managementbackend.entity.UserInfo;
import com.example.managementbackend.service.UserInfoService;
import com.example.managementbackend.web.DTO.UserInfoDTO;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
public class UserInfoController {


    private UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) { this.userInfoService = userInfoService; }

    @ModelAttribute("registration")
    public UserInfoDTO userInfoDTO() {
        return new UserInfoDTO();
    }

    @RequestMapping(value = "/registration",method = RequestMethod.GET)
    public ModelAndView registrationPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        return modelAndView;
    }
//    @GetMapping("/registration")
//    public String showRegistrationForm(ModelMap model) {
//        return "registration";
//    }
    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("registration") @Valid UserInfoDTO userInfoDTO,
                                      BindingResult result){
        boolean existing = userInfoService.findByEmail(userInfoDTO.getEmail());
        System.out.println(userInfoDTO.toString());
        if (result.equals(existing)) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            return "registration";
        }

        userInfoService.save(userInfoDTO);
        return "redirect:/registration?success";
    }


}
