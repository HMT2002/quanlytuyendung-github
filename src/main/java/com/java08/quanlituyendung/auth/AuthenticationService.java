    package com.java08.quanlituyendung.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.java08.quanlituyendung.converter.UserAccountConverter;
import com.java08.quanlituyendung.dto.*;
import com.java08.quanlituyendung.entity.*;
import com.java08.quanlituyendung.jwt.JwtService;
import com.java08.quanlituyendung.repository.*;
import com.java08.quanlituyendung.service.IMailService;
import com.java08.quanlituyendung.service.ITokenService;
import com.java08.quanlituyendung.service.impl.OtpServiceImpl;
import com.java08.quanlituyendung.utils.Constant;
import com.java08.quanlituyendung.utils.RandomNumberGenerator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserAccountRepository  userAccountRepository;
    private final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;
    private  final ITokenService tokenService;
    private  final UserAccountConverter userAccountConverter;
    private  final IMailService mailService;
    private  final UserInfoRepository userInfoRepository;

    @Autowired
    private final OtpServiceImpl otpService;
    private final OtpRepository otpRepository;

    private final UserAccountRepository userRepository;

    @Autowired
    public AuthenticationService(UserAccountRepository userAccountRepository, JwtService jwtService, AuthenticationManager authenticationManager, ITokenService tokenService, UserAccountConverter userAccountConverter, IMailService mailService, UserInfoRepository userInfoRepository, OtpServiceImpl otpService, OtpRepository otpRepository, UserAccountRepository userRepository) {
        this.userAccountRepository = userAccountRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userAccountConverter = userAccountConverter;
        this.mailService = mailService;
        this.userInfoRepository = userInfoRepository;
        this.otpService = otpService;
        this.otpRepository = otpRepository;
        this.userRepository = userRepository;
    }
    public ResponseEntity<BasicResponseDTO> sendOTP(String mail, OtpType otpType){
        try {
            Optional<UserAccountEntity> userAccountOptional = userRepository.findByEmail(mail);
            if(userAccountOptional.isPresent()){
                UserAccountEntity user = userAccountOptional.get();
                var otp_code = RandomNumberGenerator.generateSixDigitNumber();
                if(!mailService.sendVerificationMail(user.getEmail(),otp_code)){
                    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                            BasicResponseDTO.builder()
                                    .message(Constant.MAIL_FAIL)
                                    .build());
                }
                otpService.revokeAllUserOtp(user,otpType);
                otpService.saveUserOtp(user,otp_code,otpType,12000);
                mailService.sendVerificationMail(user.getEmail(),otp_code);
                return ResponseEntity.status(HttpStatus.OK).body(
                        BasicResponseDTO.builder()
                                .message(Constant.SUCCESS)
                                .build());
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    BasicResponseDTO.builder()
                            .message(Constant.MAIL_NOT_MATCH)
                            .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    BasicResponseDTO.builder()
                            .message(Constant.MAIL_FAIL+":"+e)
                            .build());
        }
    }
    public ResponseEntity<AuthenticationResponseDTO> verifyUser(VerificationRequestDTO request) {
        try{
            Optional<UserAccountEntity> userOptional = userRepository.
                    findByEmail(request.getEmail());
            if(userOptional.isPresent()){
                UserAccountEntity user = userOptional.get();
                Optional<OtpEntity> otpOptional =
                        otpRepository.findValidOtpByUser(user.getEmail(),request.getOtp(),OtpType.VERIFY);
                if(otpOptional.isPresent()){
                    OtpEntity otp = otpOptional.get();
                    otpService.revokeAllUserOtp(user,OtpType.VERIFY);
                    otp.getUser().setState(UserAccountEntity.State.ACTIVE);
                    otpRepository.save(otp);
                    var jwtToken = jwtService.generateToken(user);
                    var refreshToken = jwtService.generateRefreshToken(user);
                    return ResponseEntity.status(HttpStatus.OK).body(
                            AuthenticationResponseDTO.builder()
                                    .message(Constant.SUCCESS_VERIFY)
                                    .data(userAccountConverter.toAuthDataResponseDTO(user))
                                    .accessToken(jwtToken)
                                    .refreshToken(refreshToken)
                                    .build());
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                            AuthenticationResponseDTO.builder()
                                    .message(Constant.EXPIRED_OTP)
                                    .build());
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    AuthenticationResponseDTO.builder()
                            .message(Constant.WRONG_OTP)
                            .build());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    AuthenticationResponseDTO.builder()
                            .message(Constant.REGISTER_FAIL+":"+e)
                            .build());
        }
    }

    public ResponseEntity<AuthenticationResponseDTO> saveOrUpdateUser(OAuth2RequestDTO oAuth2RequestDTO, Role role, AuthenticationProvider authenticationProvider) {
        try{
            Optional<UserAccountEntity> userOptional = userRepository.
                    findByEmail(oAuth2RequestDTO.getEmail());
            if (userOptional.isPresent()) {
                var user = userOptional.get();
                var jwtToken = jwtService.generateToken(user);
                var refreshToken = jwtService.generateRefreshToken(user);
                tokenService.revokeAllUserToken(user); // hàm này để thu hồi refresh token
                tokenService.saveUserToken(user, refreshToken);
                //Lưu lại userInfo
                userRepository.save(user);
                // Cập nhật user info
                userInfoRepository.save(userAccountConverter.toEntity(oAuth2RequestDTO,user));
                    return ResponseEntity.status(HttpStatus.OK).body(
                            AuthenticationResponseDTO.builder()
                                    .message("Đăng nhập google thành công(tài khoản cũ)")
                                    .data(userAccountConverter.toAuthDataResponseDTO(user))
                                    .accessToken(jwtToken)
                                    .refreshToken(refreshToken)
                                    .build());

            }
            var user = userAccountConverter.toEntityWithAuthProvider(oAuth2RequestDTO, role, authenticationProvider);
            var savedUser = userAccountRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            tokenService.saveUserToken(savedUser, refreshToken);
            //tạo userInfo
            var userInfo =  createUserInfo(user);
            user.setUserInfo(userInfo);
            // cập nhật userInfo, tạm thời chỉ thêm avt từ google account
            userInfoRepository.save(userAccountConverter.toEntity(oAuth2RequestDTO,user));
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    AuthenticationResponseDTO.builder()
                            .message("Đăng nhập google thành công(tài khoản mới)")
                            .data(userAccountConverter.toAuthDataResponseDTO(user))
                            .accessToken(jwtToken)
                            .refreshToken(refreshToken)
                            .build());


        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    AuthenticationResponseDTO.builder()
                            .message("Không lấy được tài khoản"+":"+ e)
                            .build());
        }
    }

    public ResponseEntity<AuthenticationResponseDTO> register(RegisterRequestDTO request, Role role, AuthenticationProvider authenticationProvider) {
        try{
            if(request.getEmail().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        AuthenticationResponseDTO.builder()
                                .message(Constant.REGISTER_FAIL+": Missing Email!")
                                .build());
            }
            if(request.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        AuthenticationResponseDTO.builder()
                                .message(Constant.REGISTER_FAIL+": Missing Password!")
                                .build());
            }
            if(request.getUsername().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        AuthenticationResponseDTO.builder()
                                .message(Constant.REGISTER_FAIL+": Missing Username!")
                                .build());
            }
            if(userAccountRepository.existsByEmail(request.getEmail())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        AuthenticationResponseDTO.builder()
                                .message(Constant.EMAIL_EXISTED)
                                .build());
            }
            if(userAccountRepository.existsByUsername(request.getUsername())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        AuthenticationResponseDTO.builder()
                                .message(Constant.USERNAME_IS_EXIST)
                                .build());
            }
            var user = userAccountConverter.toEntityWithRole(request, role, authenticationProvider);
            var otpCode = RandomNumberGenerator.generateSixDigitNumber();
            if(!mailService.sendVerificationMail(user.getEmail(),otpCode)){
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        AuthenticationResponseDTO.builder()
                                .message(Constant.MAIL_FAIL)
                                .build());
            }
            var savedUser = userAccountRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            otpService.saveUserOtp(savedUser,otpCode,OtpType.VERIFY,120);
            tokenService.saveUserToken(savedUser, refreshToken);
            createUserInfo(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    AuthenticationResponseDTO.builder()
                            .message(Constant.SUCCESS_REGISTER)
                            .data(userAccountConverter.toAuthDataResponseDTO(user))
                            .accessToken(jwtToken)
                            .refreshToken(refreshToken)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    AuthenticationResponseDTO.builder()
                            .message(Constant.REGISTER_FAIL+":"+ e)
                            .build());
        }
    }


    public ResponseEntity<AuthenticationResponseDTO> authenticate(AuthenticationRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userAccountRepository.findByEmail(request.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            tokenService.revokeAllUserToken(user);
            tokenService.saveUserToken(user, refreshToken);

            return ResponseEntity.status(HttpStatus.OK).body(
                    AuthenticationResponseDTO.builder()
                            .message(Constant.SUCCESS_LOGIN)
                            .data(userAccountConverter.toAuthDataResponseDTO(user))
                            .accessToken(jwtToken)
                            .refreshToken(refreshToken)
                            .build());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    AuthenticationResponseDTO.builder()
                            .message(Constant.EMAIL_OR_PASSWORD_NOTMATCH)
                            .build());
        }
    }



    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null &&  tokenService.isTokenAlive(refreshToken)) {
            UserAccountEntity user = this.userAccountRepository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);

                AuthenticationResponseDTO authResponse = AuthenticationResponseDTO.builder()
                        .message(Constant.REFRESH_TOKEN_SUCCESS)
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                writeJsonResponse(response, authResponse);
            }
        }
        else {
            AuthenticationResponseDTO authResponse = AuthenticationResponseDTO.builder()
                    .message(Constant.TOKEN_IS_REVOKED)
                    .build();
            writeJsonResponse(response, authResponse);
        }
    }


    private void writeJsonResponse(HttpServletResponse response, Object responseObject) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        try (PrintWriter writer = response.getWriter()) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(writer, responseObject);
        }
    }
    private UserInfoEntity createUserInfo(UserAccountEntity userAccountEntity){
        return userInfoRepository.save(UserInfoEntity.builder()
                .userAccountInfo(userAccountEntity)
                .build());
    }
}
