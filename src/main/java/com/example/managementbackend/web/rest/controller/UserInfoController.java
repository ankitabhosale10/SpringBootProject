package com.example.managementbackend.web.rest.controller;

import com.example.managementbackend.entity.UserInfo;
import com.example.managementbackend.service.UserInfoService;
import com.example.managementbackend.web.DTO.UserInfoDTO;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/registration")
    public ResponseEntity registerUserAccount(@RequestBody UserInfoDTO userInfoDTO){
        return new ResponseEntity(userInfoService.userRegister(userInfoDTO), HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    public ResponseEntity userLogin(@RequestParam String email) throws NotFoundException {

        return new ResponseEntity(userInfoService.userlogin(email), HttpStatus.OK);
    }
}
