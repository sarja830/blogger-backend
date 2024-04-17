package com.blog.demo.service;



import com.blog.demo.dto.*;
import com.blog.demo.exceptions.EmailAlreadyExists;
import com.blog.demo.model.NotificationEmail;
import com.blog.demo.model.VerificationToken;
import com.blog.demo.model.user.RoleType;
import com.blog.demo.model.user.User;
import com.blog.demo.repository.UserRepository;
import com.blog.demo.repository.VerificationTokenRepository;
import com.blog.demo.security.JwtProvider;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    private Environment environment;


    public String signup(RegisterRequest registerRequest) {


        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent() )
            throw  new AccountStatusException("Email is already taken. if you are already registered, please, Validate your email.") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };

        if(userRepository.findByEmail(registerRequest.getUsername()).isPresent())
            throw  new AccountStatusException("Username is already taken.") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };


        var user = User.builder()
                .username(registerRequest.getUsername())
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .created(new Date())
                .roleType(RoleType.USER)
                .enabled(false)
                .build();

        userRepository.save(user);

        //generate token
        String token = generateVerificationTokenAndSave(user);
//        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to My Blog, " +
                "please click on the below url to activate your account : " +
                environment.getProperty("account.activation") + token));
        return "Account Created Successfuly, Please check your email to activate your account";
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
    }

    public String verifyAccount(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadCredentialsException("Token is invalid"));
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0)
        {
            throw new BadCredentialsException("The activation link is expired request for a new one");
        }
        fetchUserAndEnable(verificationToken);
        return "Account Activated Successfully";
    }
    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
//                .refreshToken(refreshTokenService.generateRefreshToken(loginRequest.getUsername()).getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .role(userRepository.findByUsername(loginRequest.getUsername()).get().getRoleType().name())
                .build();
    }

    public String passwordResetRequest(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Email is invalid"));


        verificationTokenRepository.deleteByUser(user.getId());
        String passwordResetToken = generateVerificationTokenAndSave(user);
        mailService.sendMail(new NotificationEmail("Password Reset Request",
                user.getEmail(), "Please click on the below url to reset your password : " +
                environment.getProperty("password.reset")+ user.getEmail()+"/"+ passwordResetToken+"/"));

        return "Password reset link has been sent to your email, please, Follow the instructions to change your password";
    }



    @Transactional
    public String passwordChange(ChangePasswordRequest changePasswordRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(changePasswordRequest.getUsername(),
                changePasswordRequest.getOldPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
//        User user = getCurrentUser();

        User user = userRepository.findByUsername(changePasswordRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found." ));
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
        return "Password changed successfully";
    }

    @Transactional
    public String passwordReset(PasswordResetRequest passwordResetRequest){

        User user = userRepository.findByEmail(passwordResetRequest.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email is invalid"));

        VerificationToken verificationToken = verificationTokenRepository.findByUserAndToken(user,passwordResetRequest.getToken())
                .orElseThrow(() -> new BadCredentialsException("Invalid Token"));

        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime()-calendar.getTime().getTime())<= 0)
        {
            throw new BadCredentialsException("The password reset link is expired request for a new one");
        }
        user.setPassword(passwordEncoder.encode(passwordResetRequest.getPassword()));
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        return "Password reset successfully";
    }

//    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
//        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
//        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername(), refreshTokenRequest
//        return AuthenticationResponse.builder()
//                .authenticationToken(token)
//                .refreshToken(refreshTokenRequest.getRefreshToken())
//                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
//                .username(refreshTokenRequest.getUsername())
//                .build();
//    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }
    public String sendVerificationTokenEmail(String email) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user Found with email : " + email));
        if (user.isEnabled())
            throw  new AccountStatusException("Account is already activated.") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };

        verificationTokenRepository.deleteByUser(user.getId());
        String token = generateVerificationTokenAndSave(user);
        mailService.sendMail(new NotificationEmail("Please Reset your Password",
                user.getEmail(), "Thank you for signing up to My Blog, " +
                "please click on the below url to activate your account : " +
                environment.getProperty("account.activation")+ token));

        return "A password Reset link has been sent to your email, please, check to Reset your account Password";
    }

    private String generateVerificationTokenAndSave(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token,user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }
    public User getUserFromJwt(Jwt jwt) {
        if(jwt==null)
            throw new UsernameNotFoundException("User not found. Invalid token");
        String username = (String) jwt.getClaims().get("sub");
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}
