package com.example.managementbackend.configuration;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.managementbackend.common.JwtUtils;
import com.example.managementbackend.common.Utils;
import com.example.managementbackend.registration.LoginData;
import com.example.managementbackend.registration.UserInfo;
import com.example.managementbackend.registration.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    final UserInfoService userInfoService;

    public CustomAuthorizationFilter(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (Utils.isValidStr(authorizationHeader) && authorizationHeader.startsWith(JwtUtils.TOKEN_PREFIX)) {
            try {
                DecodedJWT decodedJWT = JwtUtils.verifyToken(authorizationHeader);
                UserInfo userInfo = userInfoService.findByUserName(decodedJWT.getSubject());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userInfo, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                log.error("Error while authorization: {}", e.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                Utils.writeJsonResponse(response, Map.of("errorMessage", "Invalid access token"));
            }
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
