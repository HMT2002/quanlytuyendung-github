package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.dto.BasicResponseDTO;
import com.java08.quanlituyendung.dto.ChangePasswordDTO;

import com.java08.quanlituyendung.service.IOtpService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserAccountController {
    private static IOtpService otpService;
    @PutMapping("/password")
    public ResponseEntity<BasicResponseDTO> update(@RequestBody ChangePasswordDTO changePasswordDTO, Authentication authentication) {
        return otpService.changePassword(changePasswordDTO,authentication);
    }
}


