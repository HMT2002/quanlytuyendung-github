package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.auth.AuthenticationService;
import com.java08.quanlituyendung.auth.CustomOAuth2User;
import com.java08.quanlituyendung.converter.UserAccountConverter;
import com.java08.quanlituyendung.dto.*;
import com.java08.quanlituyendung.entity.AuthenticationProvider;
import com.java08.quanlituyendung.entity.OtpType;
import com.java08.quanlituyendung.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserAccountConverter userAccountConverter;

    @GetMapping("/google/callback")
    public ResponseEntity<AuthenticationResponseDTO> handleGoogleCallback(HttpServletRequest request) {
        HttpSession session = request.getSession();
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) session.getAttribute("customOAuth2User");
        return authenticationService.saveOrUpdateUser(userAccountConverter.toOAuth2RequestDTO(customOAuth2User),Role.CANDIDATE, AuthenticationProvider.GOOGLE);
    }
    @GetMapping("/error")
        public ResponseEntity<String> errorCallback() {
            String errorMessage ="error page";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @GetMapping("/google/login")
    public RedirectView loginWithGoogle() {
        return new RedirectView("/oauth2/authorization/google");
    }

    @PostMapping("/send-otp")
    public ResponseEntity<BasicResponseDTO> sendOTP(@RequestBody EmailRequestDTO request){
        return authenticationService.sendOTP(request.getEmail(), OtpType.VERIFY);
    }

    @PostMapping("/verify")
    public ResponseEntity<AuthenticationResponseDTO> verifyUser(@RequestBody VerificationRequestDTO request){
        return authenticationService.verifyUser(request);
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        return authenticationService.register(request, Role.CANDIDATE, AuthenticationProvider.LOCAL);
    }


    @PostMapping("/register-reccer")
    public ResponseEntity<AuthenticationResponseDTO> registerRECcer(@RequestBody RegisterRequestDTO request) {
        return authenticationService.register(request, Role.RECRUITER, AuthenticationProvider.LOCAL);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // Nếu validate fail thì trả về 400
    public String handleBindException(BindException e) {
        // Trả về message của lỗi đầu tiên
        String errorMessage = "Request không hợp lệ";
        if (e.getBindingResult().hasErrors())
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return errorMessage;
    }
}
