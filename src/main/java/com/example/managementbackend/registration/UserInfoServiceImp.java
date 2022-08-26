package com.example.managementbackend.registration;

import com.example.managementbackend.email.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.UUID;



@Service
@AllArgsConstructor
public class UserInfoServiceImp implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    @Override
    public UserInfo userRegister(UserInfo dto)  throws MessagingException, UnsupportedEncodingException {
        UserInfo userInfo = new UserInfo();
        userInfo.setFirstName(dto.getFirstName());
        userInfo.setLastName(dto.getLastName());
        userInfo.setEmail(dto.getEmail());
        userInfo.setPassword(passwordEncoder.encode(dto.getPassword()));
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(ts);
        dto.setActive(false);
        UUID uuid = UUID.randomUUID();
        dto.setVerificationCode(uuid.toString());
        return userInfoRepository.save(dto);
    }

    @Override
    public UserInfo findByUserName(String email) {
        return userInfoRepository.findByEmail(email);
    }


    public void sendVerificationEmail(UserInfo userInfo, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = userInfo.getEmail();
        String fromAddress = "ankitarbhosale17@gmail.com";
        String senderName = "Online Shopping";
        String subject = "Successfully registered email";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">Active Account</a></h3>"
                + "Thank you,<br>"
                + "Online Shopping";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        Context context=new Context();
        context.setVariable("name",userInfo.getFirstName());
        String verifyURL = siteURL + "/user/verify?code=" + userInfo.getVerificationCode();
        context.setVariable("URL",userInfo.getVerificationCode());
        String html=templateEngine.process("email_verification",context);

//        content = content.replace("[[name]]", userInfo.getFirstName());
//        String verifyURL = siteURL + "/user/verify?code=" + userInfo.getVerificationCode();
//        content = content.replace("[[URL]]", verifyURL);
//        helper.setText(content, true);

        helper.setText(html, true);
//        helper.setText(verifyURL,true);
        mailSender.send(message);
    }

    public boolean verify(String verificationCode) {
        UserInfo userInfo = userInfoRepository.findByVerificationCode(verificationCode);
        if (userInfo == null || userInfo.isActive()) {
            return false;
        } else {
            userInfoRepository.isActive(userInfo.getId());
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

    @Override
    public void updateResetPassword(String verificationCode, String email)  {
        UserInfo userInfo = userInfoRepository.findByEmail(email);
        if (userInfo != null) {
            userInfo.setResetPassword(verificationCode);
            userInfoRepository.save(userInfo);
        } else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }
    @Override
    public UserInfo getByResetPassword(String verificationCode) {
        return userInfoRepository.findByResetPassword(verificationCode);
    }
    @Override
    public void updatePassword(UserInfo userInfo, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        userInfo.setPassword(encodedPassword);
        userInfo.setResetPassword(null);
        userInfoRepository.save(userInfo);
    }

}
