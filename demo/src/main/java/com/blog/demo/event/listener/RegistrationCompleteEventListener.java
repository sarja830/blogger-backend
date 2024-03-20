//package com.blog.demo.event.listener;
//
//import com.blog.demo.model.NotificationEmail;
//import com.blog.demo.model.user.User;
//import com.blog.demo.service.AuthService;
//import com.blog.demo.service.MailService;
//import com.dailycodework.sbemailverificationdemo.event.RegistrationCompleteEvent;
//import com.dailycodework.sbemailverificationdemo.user.User;
//import com.dailycodework.sbemailverificationdemo.user.UserService;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.ApplicationListener;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//
//import java.io.UnsupportedEncodingException;
//import java.util.UUID;
//
///**
// * @author Sampson Alfred
// */
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
// private final AuthService authService;
// private final MailService mailService;
//// private final JavaMailSender mailSender;
// private User theUser;
//    @Override
//    public void onApplicationEvent(Registr event) {
//        // 1. Get the newly registered user
//         theUser = event.getUser();
//        //2. Create a verification token for the user
//        String verificationToken = UUID.randomUUID().toString();
//        //3. Save the verification token for the user
//        authService.saveUserVerificationToken(theUser, verificationToken);
//        //4 Build the verification url to be sent to the user
//        String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
//        //5. Send the email.
//        try {
//            sendVerificationEmail(url);
//        } catch (MessagingException | UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
//        }
//        log.info("Click the link to verify your registration :  {}", url);
//    }
//    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
//        String subject = "Email Verification";
//        String senderName = "User Registration Portal Service";
//        String mailContent = "<p> Hi, "+ theUser.getFirstName()+ ", </p>"+
//                "<p>Thank you for registering with us,"+"" +
//                "Please, follow the link below to complete your registration.</p>"+
//                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
//                "<p> Thank you <br> Users Registration Portal Service";
//        MimeMessage message = mailSender.createMimeMessage();
//        var messageHelper = new MimeMessageHelper(message);
//        messageHelper.setFrom("dailycodework@gmail.com", senderName);
//        messageHelper.setTo(theUser.getEmail());
//        messageHelper.setSubject(subject);
//        messageHelper.setText(mailContent, true);
//        mailSender.send(message);
//
//        mailService.sendMail(new NotificationEmail("Please Activate your Account",
//                user.getEmail(), "Thank you for signing up to My Blog, " +
//                "please click on the below url to activate your account : " +
//                environment.getProperty("serverUrl") + token));
//    }
//
//    public void sendPasswordResetVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
//        String subject = "Password Reset Request Verification";
//        String senderName = "User Registration Portal Service";
//        String mailContent = "<p> Hi, "+ theUser.getFirstName()+ ", </p>"+
//                "<p><b>You recently requested to reset your password,</b>"+"" +
//                "Please, follow the link below to complete the action.</p>"+
//                "<a href=\"" +url+ "\">Reset password</a>"+
//                "<p> Users Registration Portal Service";
//        MimeMessage message = mailSender.createMimeMessage();
//        var messageHelper = new MimeMessageHelper(message);
//        messageHelper.setFrom("dailycodework@gmail.com", senderName);
//        messageHelper.setTo(theUser.getEmail());
//        messageHelper.setSubject(subject);
//        messageHelper.setText(mailContent, true);
//        mailSender.send(message);
//    }
//}
