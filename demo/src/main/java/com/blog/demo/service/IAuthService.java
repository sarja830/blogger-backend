//package com.blog.demo.service;
//
//import com.blog.demo.dto.AuthenticationResponse;
//import com.blog.demo.dto.LoginRequest;
//import com.blog.demo.dto.RefreshTokenRequest;
//import com.blog.demo.dto.RegisterRequest;
//import com.blog.demo.exceptions.EmailAlreadyExists;
//import com.blog.demo.model.NotificationEmail;
//import com.blog.demo.model.VerificationToken;
//import com.blog.demo.model.user.RoleType;
//import com.blog.demo.model.user.User;
//import com.dailycodework.sbemailverificationdemo.registration.RegistrationRequest;
//import com.dailycodework.sbemailverificationdemo.registration.token.VerificationToken;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Instant;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
///**
// * @author Sampson Alfred
// */
//
//public interface IAuthService {
//
//     void signup(RegisterRequest registerRequest);
//     User getCurrentUser();
//
//     void verifyAccount(String token);
//     AuthenticationResponse login(LoginRequest loginRequest);
//     AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
//     boolean isLoggedIn();
//
//
//
//
//
//
//    List<User> getUsers();
//
//    User registerUser(RegisterRequest request);
//
//    Optional<User> findByEmail(String email);
//
//    void saveUserVerificationToken(User theUser, String verificationToken);
//
//    String validateToken(String theToken);
//
//    VerificationToken generateNewVerificationToken(String oldToken);
//    void changePassword(User theUser, String newPassword);
//
//    String validatePasswordResetToken(String token);
//
//    User findUserByPasswordToken(String token);
//
//    void createPasswordResetTokenForUser(User user, String passwordResetToken);
//
//    boolean oldPasswordIsValid(User user, String oldPassword);
//}
