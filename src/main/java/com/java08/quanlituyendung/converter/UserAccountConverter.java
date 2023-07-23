package com.java08.quanlituyendung.converter;


import com.java08.quanlituyendung.auth.CustomOAuth2User;
import com.java08.quanlituyendung.dto.AuthDataResponseDTO;
import com.java08.quanlituyendung.dto.OAuth2RequestDTO;
import com.java08.quanlituyendung.dto.ProfileUpdateRequestDTO;
import com.java08.quanlituyendung.dto.RegisterRequestDTO;
import com.java08.quanlituyendung.entity.AuthenticationProvider;
import com.java08.quanlituyendung.entity.Role;
import com.java08.quanlituyendung.entity.UserAccountEntity;
import com.java08.quanlituyendung.entity.UserInfoEntity;
import com.java08.quanlituyendung.repository.UserAccountRepository;
import com.java08.quanlituyendung.utils.RandomNumberGenerator;
import com.java08.quanlituyendung.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserAccountConverter {

    private final PasswordEncoder passwordEncoder;
    private final UserAccountRepository userAccountRepository;

    @Autowired
    UserInfoRepository userInfoRepository;
    public UserAccountEntity toEntity(RegisterRequestDTO registerRequestDTO) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setEmail(registerRequestDTO.getEmail());
        entity.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        entity.setUsername(registerRequestDTO.getUsername());
        return entity;
    }
    public UserInfoEntity toEntity(ProfileUpdateRequestDTO profileUpdateRequest, UserAccountEntity userAccount) {
        UserInfoEntity userInfoEntity = userInfoRepository.findUserInfoEntityByUserAccountInfo(userAccount);
        userInfoEntity.setFullName(profileUpdateRequest.getFullName());
        userInfoEntity.setPhone(profileUpdateRequest.getPhone());
        userInfoEntity.setAddress(profileUpdateRequest.getAddress());
        userInfoEntity.setGender(profileUpdateRequest.getGender());
        userInfoEntity.setDob(profileUpdateRequest.getDob());
        if(profileUpdateRequest.getCv()!=null){
        userInfoEntity.setCv(profileUpdateRequest.getCv().toString());

        }
        else{
                    userInfoEntity.setCv(null);

        }
        userInfoEntity.setAvatar(profileUpdateRequest.getAvatar());
        return userInfoEntity;
    }
    public UserInfoEntity toEntity(OAuth2RequestDTO oAuth2RequestDTO, UserAccountEntity userAccount) {
        UserInfoEntity userInfoEntity = userInfoRepository.findUserInfoEntityByUserAccountInfo(userAccount);
        //userInfoEntity.setFullName(oAuth2RequestDTO.getUsername());
        //userInfoEntity.setPhone(oAuth2RequestDTO.getPhone());
        //userInfoEntity.setAddress(.getAddress());
        //userInfoEntity.setGender(.getGender());
        userInfoEntity.setAvatar(oAuth2RequestDTO.getAvatar());
        return userInfoEntity;
    }

// convert for local
    public UserAccountEntity toEntityWithRole(RegisterRequestDTO registerRequestDTO, Role roleEnum, AuthenticationProvider authenticationProvider) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUsername(registerRequestDTO.getUsername());
        entity.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        entity.setEmail(registerRequestDTO.getEmail());
        entity.setState(UserAccountEntity.State.UNAUTHENTICATED);
        entity.setCreationTime(LocalDateTime.now());
        entity.setRole(roleEnum);
        entity.setAuthenticationProvider(authenticationProvider);
        return entity;
    }

    // convert cho register google
    public UserAccountEntity toEntityWithAuthProvider(OAuth2RequestDTO oAuth2RequestDTO, Role roleEnum, AuthenticationProvider authenticationProvider) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setUsername(convertToLowerCase(oAuth2RequestDTO.getUsername()));
        entity.setAuthenticationProvider(authenticationProvider);
        entity.setEmail(oAuth2RequestDTO.getEmail());
        entity.setCreationTime(LocalDateTime.now());
        entity.setRole(roleEnum);
        entity.setState(UserAccountEntity.State.ACTIVE);
        return userAccountRepository.save(entity);
    }


    public AuthDataResponseDTO toAuthDataResponseDTO(UserAccountEntity userAccountEntity) {
        AuthDataResponseDTO authDataResponseDTO = new AuthDataResponseDTO();
        authDataResponseDTO.setUsername(userAccountEntity.getUsernameReal());
        authDataResponseDTO.setEmail(userAccountEntity.getEmail());
        authDataResponseDTO.setUserInfoEntity(userAccountEntity.getUserInfo());
        authDataResponseDTO.setState(userAccountEntity.getState().toString());
        authDataResponseDTO.setRole(userAccountEntity.getRole().toString());
        return authDataResponseDTO;
    }


    public OAuth2RequestDTO toOAuth2RequestDTO(CustomOAuth2User customOAuth2User) {
        OAuth2RequestDTO oAuth2RequestDTO = new OAuth2RequestDTO();
        oAuth2RequestDTO.setUsername(convertToLowerCase(customOAuth2User.getName())+RandomNumberGenerator.generateSixDigitNumber());
        oAuth2RequestDTO.setEmail(customOAuth2User.getEmail());
        oAuth2RequestDTO.setAvatar(customOAuth2User.getAvatar());
        return oAuth2RequestDTO;
    }


    public static String convertToLowerCase(String input) {
        String convertedString = input.toLowerCase();
        convertedString = convertedString.replaceAll(" ", "_");
        convertedString = removeDiacritics(convertedString).replaceAll("đ","d");
        return convertedString;
    }

    private static String removeDiacritics(String input) {
        //input = input.replaceAll("Đ", "D");
        String normalizedString = java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD);
        return normalizedString.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
