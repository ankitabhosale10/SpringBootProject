package com.example.managementbackend.controller;

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
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserInfoRepository repo;
    private UserInfoService userInfoService;

    private BCryptPasswordEncoder passwordEncoder;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/api/register")
    public ModelAndView signup(@Valid @ModelAttribute UserInfo dto, BindingResult result) {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = repo.findByEmail(dto.getEmail());
        if (userInfo == null) {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            dto.setCreatedDate(ts);
            dto.setActive(false);
            dto.setDeleted(false);
            UUID uuid = UUID.randomUUID();
            dto.setVerificationCode(uuid.toString());
            repo.save(dto);
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
    public ModelAndView processForm(@Valid @ModelAttribute("loginData") UserInfo loginData, BindingResult result) {
        UserInfo userInfo = repo.findByEmail(loginData.getEmail());
        ModelAndView mv = new ModelAndView();
        if (userInfo != null) {
            if (userInfo.getPassword().equals(loginData.getPassword())) {
                mv.setViewName("success");
                mv.addObject("message", "Thanks For Login");
                mv.addObject("loginData", loginData);
                return mv;
            } else {
                mv.setViewName("login");
                mv.addObject("message", "Invalid Credential");
                return mv;
            }
        } else {
            mv.setViewName("login");
            mv.addObject("message", "valid Credential");
            return mv;
        }

    }

    @GetMapping("/account-verification/{authToken}")
    public ModelAndView checkToken(@RequestParam("authToken") String authToken) {
        UserInfo dto = repo.findByVerificationCode(authToken);
        ModelAndView mv = new ModelAndView();
        if (dto != null) {
            dto.setActive(true);
            dto.setVerificationCode(null);
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            dto.setModifiedDate(ts);
            repo.save(dto);
            mv.setViewName("verification-success");
            mv.addObject("message", "Your Account is verified");
            return mv;
        } else {
            mv.setViewName("verification-failed");
            mv.addObject("message", "Verification link is expired");
            return mv;
        }
    }
}
