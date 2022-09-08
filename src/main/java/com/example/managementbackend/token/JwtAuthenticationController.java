package com.example.managementbackend.token;

import com.example.managementbackend.configuration.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtUserDetailService jwtUserDetailService;
//
//    @RequestMapping(value = "/authenticate_token", method = RequestMethod.POST)
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception
//    {
//
//        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
//
//        final UserDetails userDetails = jwtUserDetailService.loadUserByUsername(authenticationRequest.getEmail());
//
//        final String token = jwtTokenUtil.generateToken((JwtRequest) userDetails);
//
//        return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    private void authenticate(String email, String password) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }
@Autowired
private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/authenticate_token", method = RequestMethod.POST)
    public String getToken(@RequestBody JwtRequest authenticationRequest)  {
        final String token = jwtTokenUtil.generateToken(authenticationRequest);
        return token;
    }

    @RequestMapping(value = "/check_authenticate_token", method = RequestMethod.POST)
    public boolean checkToken(@RequestBody JwtRequest authenticationRequest)  {
        final boolean token = jwtTokenUtil.validateToken(authenticationRequest.getToken(), authenticationRequest);
        return token;
    }

}
