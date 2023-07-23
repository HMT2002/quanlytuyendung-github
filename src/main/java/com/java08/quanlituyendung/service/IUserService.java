package com.java08.quanlituyendung.service;

import com.java08.quanlituyendung.dto.*;
import com.java08.quanlituyendung.entity.UserAccountEntity;
import com.java08.quanlituyendung.entity.UserInfoEntity;

import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

public interface IUserService {
    ResponseEntity<ResponseObject> updateProfile(ProfileUpdateRequestDTO request, Authentication authentication);
    ResponseEntity<ResponseObject> getProfile(Authentication authentication) throws ParseException;



    void createUserInfo(UserAccountEntity userAccountEntity);

}
