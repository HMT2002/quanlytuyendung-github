package com.java08.quanlituyendung.service.impl;

import com.java08.quanlituyendung.auth.AuthenticationService;
import com.java08.quanlituyendung.converter.UserAccountConverter;
import com.java08.quanlituyendung.dto.ProfileUpdateRequestDTO;
import com.java08.quanlituyendung.dto.ResponseObject;
import com.java08.quanlituyendung.entity.UserAccountEntity;
import com.java08.quanlituyendung.entity.UserInfoEntity;
import com.java08.quanlituyendung.jwt.JwtService;
import com.java08.quanlituyendung.repository.UserAccountRepository;
import com.java08.quanlituyendung.repository.UserInfoRepository;
import com.java08.quanlituyendung.service.IMailService;
import com.java08.quanlituyendung.service.ITokenService;
import com.java08.quanlituyendung.service.IUserService;
import com.java08.quanlituyendung.utils.Constant;
import lombok.RequiredArgsConstructor;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
        @Autowired
        UserAccountRepository userAccountRepository;
        @Autowired
        UserInfoRepository userInfoRepository;
        @Autowired
        UserAccountConverter userAccountConverter;
        @Autowired
        AuthenticationService authenticationService;
        @Autowired
        JwtService jwtService;

        @Override
        public ResponseEntity<ResponseObject> updateProfile(ProfileUpdateRequestDTO request,
                        Authentication authentication) {
                if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
                        String userEmail = userDetails.getUsername();
                        Optional<UserAccountEntity> userAccount = userAccountRepository.findByEmail(userEmail);
                        if (userAccount.isPresent()) {
                                var userAccount1 = userAccount.get();
                                return ResponseEntity.status(HttpStatus.OK).body(
                                                ResponseObject.builder()
                                                                .status("OK")
                                                                .message((String) Constant.UPDATE_PROFILE_SUCCESS)
                                                                .data(userInfoRepository.save(userAccountConverter
                                                                                .toEntity(request, userAccount1)))
                                                                .build());
                        } else
                                return ResponseEntity.status(HttpStatus.OK).body(
                                                ResponseObject.builder()
                                                                .status("OK")
                                                                .message("Không tồn tại user")
                                                                .data("").build());
                }
                return ResponseEntity.status(HttpStatus.OK).body(
                                ResponseObject.builder()
                                                .status("OK")
                                                .message("Xác thực thất bại")
                                                .data("").build());
        }

        @Override
        public ResponseEntity<ResponseObject> getProfile(Authentication authentication) throws ParseException {
                // if (authentication != null && authentication.getPrincipal() instanceof
                // UserDetails userDetails) {
                // String userEmail = userDetails.getUsername();
                // Optional<UserAccountEntity> userAccount =
                // userAccountRepository.findByEmail(userEmail);
                // long userId = Math.toIntExact(userAccount.get().getId());
                // Optional<UserInfoEntity> userInfo = userInfoRepository.findById(userId);
                // if (userAccount.isPresent()) {
                // return ResponseEntity.status(HttpStatus.OK).body(
                // ResponseObject.builder()
                // .status("OK")
                // .message(Constant.SUCCESS)
                // .data(userInfo).build());
                // } else
                // return ResponseEntity.status(HttpStatus.OK).body(
                // ResponseObject.builder()
                // .status("OK")
                // .message("Không lấy được userInfo")
                // .data("").build());
                // }
                // return ResponseEntity.status(HttpStatus.OK).body(
                // ResponseObject.builder()
                // .status("OK")
                // .message("Xác thực thất bại")
                // .data("").build());

                if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
                        String userEmail = userDetails.getUsername();
                        Optional<UserAccountEntity> userAccount = userAccountRepository.findByEmail(userEmail);
                        long userId = Math.toIntExact(userAccount.get().getId());
                        Optional<UserInfoEntity> userInfo = userInfoRepository.findById(userId);
                        if (userAccount.isPresent()) {
                                JSONObject obj = new JSONObject();
                                obj.put("name", userInfo.get().getFullName());
                                obj.put("avatar", userInfo.get().getAvatar());
                                obj.put("gender", userInfo.get().getGender());
                                obj.put("email", userAccount.get().getEmail());
                                obj.put("phone", userInfo.get().getPhone());
                                obj.put("address", userInfo.get().getAddress());

                                if (userInfo.get().getCv() != null) {
                                        JSONParser parser = new JSONParser();
                                        JSONObject cvObj = (JSONObject) parser.parse(userInfo.get().getCv());
                                        obj.put("cv", cvObj);
                                } else {
                                        obj.put("cv", null);

                                }

                                return ResponseEntity.status(HttpStatus.OK).body(
                                                ResponseObject.builder()
                                                                .status("OK")
                                                                .message(Constant.SUCCESS)
                                                                .data(obj).build());
                        } else
                                return ResponseEntity.status(HttpStatus.OK).body(
                                                ResponseObject.builder()
                                                                .status("OK")
                                                                .message("Không lấy được userInfo")
                                                                .data("").build());
                }
                return ResponseEntity.status(HttpStatus.OK).body(
                                ResponseObject.builder()
                                                .status("OK")
                                                .message("Xác thực thất bại")
                                                .data("").build());

        }

        @Override
        public void createUserInfo(UserAccountEntity userAccountEntity) {
                userInfoRepository.save(UserInfoEntity.builder()
                                .userAccountInfo(userAccountEntity)
                                .build());
        }
}
