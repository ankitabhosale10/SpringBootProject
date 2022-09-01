package com.example.managementbackend.controller;

import com.example.managementbackend.registration.UserInfo;
import com.example.managementbackend.registration.UserInfoService;
import com.example.managementbackend.registration.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Controller
public class ForgotPasswordController<UserNotFoundException extends Throwable> {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        UUID uuid = UUID.randomUUID();
        String verificationCode = uuid.toString();
        try {
            userInfoService.updateResetPassword(verificationCode, email);
            String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?code=" + verificationCode;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException | MessagingException | UnsupportedEncodingException ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "forgot_password_form";
    }

    public void sendEmail(String recipientEmail, String link) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("ankitarbhosale17@gmail.com", "Online Shopping ");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @GetMapping("/reset_password")
    public ModelAndView showResetPasswordForm(@Param(value = "code") String Code, Model model) {
        ModelAndView mv = new ModelAndView();
        userInfoService.getByResetPassword(Code);
        mv.setViewName("reset_password_form");
        return mv;
    }

    @PostMapping("/reset/password")
    public ModelAndView processResetPassword(HttpServletRequest request, Model model) {
//        String verificationCode = request.getParameter("verificationCode");

        String password = request.getParameter("verificationCode");
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userInfoService.getByResetPassword(password);
            userInfoService.updatePassword(userInfo, password);
            mv.setViewName("message");
            return mv;
    }
}
