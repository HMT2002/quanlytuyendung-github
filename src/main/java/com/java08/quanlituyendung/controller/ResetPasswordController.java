package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.auth.AuthenticationService;
import com.java08.quanlituyendung.dto.*;
import com.java08.quanlituyendung.entity.OtpType;
import com.java08.quanlituyendung.service.IOtpService;
import com.java08.quanlituyendung.service.IUserService;
import com.java08.quanlituyendung.service.impl.OtpServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recover")
@RequiredArgsConstructor
public class ResetPasswordController {
    private final AuthenticationService authenticationService;
    private final IOtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<BasicResponseDTO> sendOTP(@RequestBody EmailRequestDTO request ){
        return authenticationService.sendOTP(request.getEmail(),OtpType.RESET_PASSWORD);
    }
    @PostMapping("/verify")
    public ResponseEntity<ForgotPasswordResponseDTO> verifyUser(@RequestBody VerificationRequestDTO request){
        return otpService.verifyUser(request, OtpType.RESET_PASSWORD);
    }
    @PutMapping("/password")
    public ResponseEntity<BasicResponseDTO> createNewPassword(
            @RequestBody ResetPasswordDTO request,
            @RequestParam("u") String id,
            @RequestParam("o") String otp){
        return otpService.createNewPassword(request, id,otp);
    }
}
