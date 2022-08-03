package com.example.managementbackend.web.rest.controller;

import com.example.managementbackend.entity.LoginData;
import com.example.managementbackend.service.UserInfoService;
import com.example.managementbackend.web.DTO.UserInfoDTO;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    private UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) { this.userInfoService = userInfoService; }

    @PostMapping("/registration")
    public ResponseEntity registerUserAccount(@RequestBody UserInfoDTO userInfoDTO){
        return new ResponseEntity(userInfoService.userRegister(userInfoDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/userLogin")
    public ResponseEntity userLogin(@RequestParam String email,@RequestParam String password) throws NotFoundException {
        return new ResponseEntity(userInfoService.userlogin(email,password), HttpStatus.OK);
    }

    @GetMapping("/loginForm")
    public ModelAndView openForm(Model model){
        ModelAndView modelAndView=new ModelAndView();
        model.addAttribute("loginData",new LoginData()).toString();
      return modelAndView;
    }

//    handler for process form
    @PostMapping("/submit")
    public ModelAndView processForm(@Valid @ModelAttribute ("loginData") LoginData loginData, BindingResult result){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("success");
        return modelAndView;
    }
}
