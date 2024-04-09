package com.blog.demo.controller;

import com.blog.demo.dto.*;
import com.blog.demo.exceptions.EmailAlreadyExists;
import com.blog.demo.exceptions.Result;
import com.blog.demo.exceptions.StatusCode;
import com.blog.demo.service.AuthService;
import com.blog.demo.service.RefreshTokenService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;



import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@Slf4j
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public Result signup(@RequestBody RegisterRequest registerRequest) throws EmailAlreadyExists {
        return new Result(true, StatusCode.SUCCESS, "Account Created successfuly", this.authService.signup(registerRequest));
    }

    @GetMapping("/accountVerification/{token}")
    public Result verifyAccount(@PathVariable String token) {
        return new Result(true, StatusCode.SUCCESS, "Account activated successfuly", this.authService.verifyAccount(token));

    }

    @PostMapping("/signin")
    public Result login(@RequestBody LoginRequest loginRequest) {

        return new Result(true, StatusCode.SUCCESS, "User Info and JSON Web Token", this.authService.login(loginRequest));

    }

//    @PostMapping("/refresh/token")
//    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
//        return authService.refreshToken(refreshTokenRequest);
//    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!!");
    }

    @GetMapping("/resend-verification-token")
    public Result resendVerificationToken(@RequestParam("email") String email) {
        return new Result(true, StatusCode.SUCCESS, "User Info and JSON Web Token", this.authService.sendVerificationTokenEmail(email));
    }

    @PostMapping("/passwordResetRequest")
    public Result resetPasswordRequest(@RequestBody RequestPasswordResetRequest requestPasswordResetRequest) {
        return new Result(true, StatusCode.SUCCESS, "User Info and JSON Web Token", this.authService.passwordResetRequest(requestPasswordResetRequest.getEmail()));
    }

    //
    @PostMapping("/resetPassword")
    public Result resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        return new Result(true, StatusCode.SUCCESS, "User Info and JSON Web Token", this.authService.passwordReset(passwordResetRequest));
    }

    @PostMapping("/changePassword")
    public Result changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return new Result(true, StatusCode.SUCCESS, "User Info and JSON Web Token", this.authService.passwordChange(changePasswordRequest));
    }
}