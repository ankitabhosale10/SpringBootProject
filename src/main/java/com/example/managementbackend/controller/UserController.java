package com.example.managementbackend.controller;

import com.example.managementbackend.entity.LoginData;
import com.example.managementbackend.entity.UserInfo;
import com.example.managementbackend.repository.UserInfoRepository;
import com.example.managementbackend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Timestamp;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserInfoService userInfoService;

    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    private UserInfoRepository repo;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/api/register")
    public ModelAndView signup(@ModelAttribute UserInfo dto) {

        System.out.println("registration method calling");
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(ts);
        System.out.println(dto.toString());
        repo.save(dto);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("welcome");
        mv.addObject("message", "Thanks For Registration");
        mv.addObject("result", dto);
        return mv;
    }

    @PostMapping("/api/login")
    public ModelAndView processForm(@Valid @ModelAttribute("loginData") LoginData loginData, BindingResult result) {

        System.out.println(loginData.toString());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success");
        modelAndView.addObject("loginData", loginData);
        return modelAndView;
    }

//    @PostMapping("/registration")
//    public ResponseEntity registerUserAccount(@RequestBody UserInfoDTO userInfoDTO){
//
//        return new ResponseEntity(userInfoService.userRegister(userInfoDTO), HttpStatus.OK);
//    }

//    @GetMapping(value = "/userLogin")
//    public ResponseEntity userLogin(@RequestParam String email,@RequestParam String password) throws NotFoundException {
//        return new ResponseEntity(userInfoService.userlogin(email,password), HttpStatus.OK);
//    }


}
