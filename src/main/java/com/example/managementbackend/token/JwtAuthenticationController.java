package com.example.managementbackend.token;

import com.example.managementbackend.configuration.JwtUserDetailService;
import com.example.managementbackend.registration.UserInfo;
import com.example.managementbackend.registration.UserInfoRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @RequestMapping(value = "/auth-sign-in", method = RequestMethod.POST)
    public String signin(@RequestBody JwtRequest authenticationRequest) {

        UserInfo userInfo = userInfoRepository.getByUserEmail(authenticationRequest.getEmail());
        if (userInfo == null) {
            return "Invalid Credential";
        } else {

            if (userInfo.getPassword().equalsIgnoreCase(authenticationRequest.getPassword())) {
                String token = jwtTokenUtil.generateToken(authenticationRequest);
                userInfo.setAuthToken(token);
                userInfoRepository.save(userInfo);
                return token;
            }
            return "Invalid Credential";
        }
    }

    @RequestMapping(value = "/auth-sign-up", method = RequestMethod.POST)
    public String signup(@RequestBody JwtRequest authenticationRequest) {

        UserInfo userInfo = userInfoRepository.getByUserEmail(authenticationRequest.getEmail());
        if (userInfo == null) {
            UserInfo newUser = new UserInfo();
            newUser.setEmail(authenticationRequest.getEmail());
            newUser.setPassword(authenticationRequest.getPassword());
            newUser = userInfoRepository.save(newUser);
            return newUser.toString() + " New Account Created";
        } else {
            return "Email already exist";

        }
    }

    @RequestMapping(value = "/my-info", method = RequestMethod.GET)
    public Map<Object, Object> myInfo(HttpServletRequest request) {

        Map<Object, Object> response = new HashMap<>();
        String token = request.getHeader("Authorization");
        if (token != null) {

            UserInfo userInfo = userInfoRepository.getByUserAuthToken(token);
            if (userInfo != null) {
                response.put("data", userInfo);
                return response;
            }
        }
        response.put("message", "invalid token");
        return response;

    }


    @RequestMapping(value = "/check_authenticate_token", method = RequestMethod.POST)
    public boolean checkToken(@RequestBody JwtRequest authenticationRequest) {
        final boolean token = jwtTokenUtil.validateToken(authenticationRequest.getToken(), authenticationRequest);
        return token;
    }

}
