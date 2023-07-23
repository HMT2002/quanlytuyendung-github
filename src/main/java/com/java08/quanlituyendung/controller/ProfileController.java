package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.dto.ProfileUpdateRequestDTO;
import com.java08.quanlituyendung.dto.ResponseObject;
import com.java08.quanlituyendung.service.IUserService;
import lombok.RequiredArgsConstructor;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    @Autowired
    private IUserService userService;
    @GetMapping
    public ResponseEntity<ResponseObject> get(Authentication authentication) throws ParseException{
        
        return userService.getProfile(authentication);
    }

    @PutMapping
    public ResponseEntity<ResponseObject> update(@RequestBody ProfileUpdateRequestDTO profileUpdateRequestDTO, Authentication authentication) {
                return userService.updateProfile(profileUpdateRequestDTO,authentication);
    }
}
