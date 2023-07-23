package com.java08.quanlituyendung.service;

import com.java08.quanlituyendung.dto.*;
import com.java08.quanlituyendung.entity.OtpType;
import com.java08.quanlituyendung.entity.UserAccountEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface IOtpService {
    public void saveUserOtp(UserAccountEntity user, String otpCode, OtpType otpType, long expInSeconds);

    public void revokeAllUserOtp(UserAccountEntity user, OtpType otpType) ;

    public ResponseEntity<ForgotPasswordResponseDTO> verifyUser(VerificationRequestDTO request, OtpType otpType);

    public ResponseEntity<BasicResponseDTO> createNewPassword(ResetPasswordDTO request, String id, String otp);
    ResponseEntity<BasicResponseDTO> changePassword(ChangePasswordDTO request, Authentication authentication);
}
