package com.example.managementbackend.service.Imp;


import com.example.managementbackend.repository.UserInfoRepository;
import com.example.managementbackend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImp implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;



}
