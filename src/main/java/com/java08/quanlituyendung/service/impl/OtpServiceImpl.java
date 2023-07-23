package com.java08.quanlituyendung.service.impl;

import com.java08.quanlituyendung.dto.*;
import com.java08.quanlituyendung.entity.*;
import com.java08.quanlituyendung.repository.OtpRepository;
import com.java08.quanlituyendung.repository.UserAccountRepository;
import com.java08.quanlituyendung.service.IOtpService;
import com.java08.quanlituyendung.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements IOtpService {
    private final UserAccountRepository userAccountRepository;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void saveUserOtp(UserAccountEntity user, String otpCode, OtpType otpType, long expInSeconds) {
        var otp = OtpEntity.builder()
                .user(user)
                .otp(otpCode)
                .otpType(otpType)
                .creationTime(LocalDateTime.now())
                .expiredTime(LocalDateTime.now().plusSeconds(expInSeconds))
                .revoked(false)
                .build();
        otpRepository.save(otp);

    }

    @Override
    public void revokeAllUserOtp(UserAccountEntity user, OtpType otpType) {
        var validUserOTPs = otpRepository.findAllValidOtpByUser(user.getId(),otpType);
        if(validUserOTPs.isEmpty()) {
            return;
        }
        validUserOTPs.forEach(otp -> {
            otp.setRevoked(true);
        });
        otpRepository.saveAll(validUserOTPs);
    }
    @Override
    public ResponseEntity<BasicResponseDTO> createNewPassword(ResetPasswordDTO request, String id, String code) {
        try{
            if(request.getPassword().equals(request.getConfirmPassword())){
                Optional<OtpEntity> otpOptional =
                        otpRepository.findValidOtpByUserID(Long.parseLong(id),code,OtpType.RESET_PASSWORD);
                if(otpOptional.isPresent()){
                    OtpEntity otp = otpOptional.get();
                    otp.user.setPassword(passwordEncoder.encode(request.getPassword()));
                    otp.setRevoked(true);
                    otpRepository.save(otp);
                    return ResponseEntity.status(HttpStatus.OK).body(
                            BasicResponseDTO.builder()
                                    .message(Constant.SUCCESS)
                                    .build());
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        BasicResponseDTO.builder()
                                .message(Constant.WRONG_OTP)
                                .build());
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    BasicResponseDTO.builder()
                            .message("Password not match confirm Password")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    BasicResponseDTO.builder()
                            .message(Constant.REGISTER_FAIL+":"+e.toString())
                            .build());
        }
    }
    @Override
    public ResponseEntity<BasicResponseDTO> changePassword(ChangePasswordDTO request,Authentication authentication) {
        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    BasicResponseDTO.builder()
                            .message("Wrong confirm password!")
                            .build());
        }else {
            if(authentication != null && authentication.getPrincipal() instanceof  UserDetails userDetails){
                String userEmail = userDetails.getUsername();
                Optional<UserAccountEntity> userOptional = userAccountRepository.findByEmail(userEmail);
                if(userOptional.isPresent()){
                    UserAccountEntity user = userOptional.get();
                    if(user.getPassword().equals(passwordEncoder.encode(request.getPassword()))){
                        user.setPassword(passwordEncoder.encode(request.getPassword()));
                        userAccountRepository.save(user);
                        return ResponseEntity.status(HttpStatus.OK).body(
                                BasicResponseDTO.builder()
                                        .message("Update password successfully!")
                                        .build());
                    }
                    else {
                        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                                BasicResponseDTO.builder()
                                        .message("Wrong Password!")
                                        .build());
                    }
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    BasicResponseDTO.builder()
                            .message("False authentication!")
                            .build());
        }
    }
    @Override
    public ResponseEntity<ForgotPasswordResponseDTO> verifyUser(VerificationRequestDTO request, OtpType otpType) {
        try{
            Optional<OtpEntity> otpOptional = otpRepository.
                    findValidOtpByUser(request.getEmail(),request.getOtp(),otpType);
            if(otpOptional.isPresent()){
                OtpEntity otp = otpOptional.get();
                otp.setRevoked(true);
                return ResponseEntity.status(HttpStatus.OK).body(
                        ForgotPasswordResponseDTO.builder()
                                .message(Constant.SUCCESS)
                                .data(ForgotPasswordDTO.builder()
                                        .otp(otp.getOtp())
                                        .userId(otp.user.getId()).build())
                                .build());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    ForgotPasswordResponseDTO.builder()
                            .message(Constant.WRONG_OTP)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    ForgotPasswordResponseDTO.builder()
                            .message(Constant.REGISTER_FAIL+":"+e.toString())
                            .build());
        }
    }

}
