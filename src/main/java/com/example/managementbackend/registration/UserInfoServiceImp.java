package com.example.managementbackend.registration;


import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserInfoServiceImp implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void userRegister(UserInfo dto, String siteURL)  throws MessagingException, UnsupportedEncodingException {
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(dto.getFirstName());
        userInfo.setLastName(dto.getLastName());
        userInfo.setEmail(dto.getEmail());
        String encodedPassword = passwordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(encodedPassword);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(ts);
        dto.setActive(false);
        String randomCode = RandomString.make(64);
        userInfo.setVerificationCode(randomCode);
        sendVerificationEmail(userInfo, siteURL);
        userInfoRepository.save(dto);
    }


    @Override
    public void sendVerificationEmail(UserInfo userInfo, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = userInfo.getEmail();
        String fromAddress = "ankitarbhosale@gmail.com";
        String senderName = "Online Shopping";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Online Shopping";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", userInfo.getFirstName());
        String verifyURL = siteURL + "/verify?code=" + userInfo.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public boolean verify(String verificationCode) {
        UserInfo userInfo = userInfoRepository.findByVerificationCode(verificationCode);

        if (userInfo == null || userInfo.isActive()) {
            return false;
        } else {
            userInfo.setVerificationCode(null);
            userInfo.setActive(true);
            userInfoRepository.save(userInfo);
            return true;
        }
    }
    @Override
    public UserInfo userLogin(LoginData loginData) {
        UserInfo userInfo = new UserInfo();
        if (loginData == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return userInfoRepository.findByEmail(loginData.getEmail());
    }


//    @Override
//    public UserInfo userVerificationCode(String authToken) {
//        UserInfo dto=new UserInfo();
//        dto.setActive(true);
//        dto.setVerificationCode(null);
//        Timestamp ts = new Timestamp(System.currentTimeMillis());
//        dto.setModifiedDate(ts);
//        return userInfoRepository.save(dto);
//    }

}
