package com.example.managementbackend.registration;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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

    @Override
    public void sendVerificationEmail(UserInfo userInfo, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = userInfo.getEmail();
        String fromAddress = "ankitarbhosale17@gmail.com";
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
        String verifyURL = siteURL + "/user/verify?code=" + userInfo.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

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

}
