package com.example.managementbackend.controller;

import com.example.managementbackend.token.JwtRequest;
import com.example.managementbackend.registration.*;
import com.example.managementbackend.token.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoService userInfoService;


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/api/register")
    public ModelAndView signup(@ModelAttribute UserInfo userInfo, @RequestParam(value = "checkMeOut", defaultValue = "false") boolean exampleCheck1, Model model, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo1 = userInfoService.findByUserName(userInfo.getEmail());
        if (userInfo1 != null) {
            mv.setViewName("user");
            return mv;
        }
        userInfoService.userRegister(userInfo);
        String siteURL = Utility.getSiteURL(request);
        userInfoService.sendVerificationEmail(userInfo, siteURL);
        mv.setViewName("register_success");
        return mv;
    }

    @GetMapping("/verify")
    public ModelAndView verifyUser(@Param("code") String code, Model model) {
        ModelAndView mv = new ModelAndView();
        boolean verified = userInfoService.verify(code);
        if (verified == true) {
            mv.setViewName("verify_success");
            return mv;
        }
        mv.setViewName("verify_fail");
        return mv;
    }

    @PostMapping("/api/login")
    public ModelAndView processForm(@ModelAttribute JwtRequest userLogin) {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userInfoService.userLogin(userLogin);
        if (userInfo != null && (userInfo.getPassword().equals(userLogin.getPassword()) && userInfo.isActive())) {

            String token = jwtTokenUtil.generateToken(userLogin);

            userInfo.setAuthToken(token);
            userInfo = userInfoRepository.save(userInfo);

            mv.setViewName("success");

          //  mv.addObject("token", token);
            mv.addObject("message", "Thanks For Login");
            mv.addObject("loginData", userInfo);
            return mv;
        }
        mv.setViewName("login");
        mv.addObject("message", "Invalid Credential");
        return mv;
    }

}
