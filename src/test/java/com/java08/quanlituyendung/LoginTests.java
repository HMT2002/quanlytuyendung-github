package com.java08.quanlituyendung;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java08.quanlituyendung.auth.AuthenticationService;
import com.java08.quanlituyendung.dto.AuthenticationRequestDTO;
import com.java08.quanlituyendung.dto.AuthenticationResponseDTO;

import com.java08.quanlituyendung.controller.AuthController;
import com.java08.quanlituyendung.dto.RegisterRequestDTO;
import com.java08.quanlituyendung.entity.*;
import com.java08.quanlituyendung.repository.UserAccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoginTests {

    @Autowired
    private MockMvc mockMvc;



    @Autowired
    private UserAccountRepository userAccountRepository;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testLoginSuccess() throws Exception {
        String email = "test@gmail.com";
        String username = "testlogin";
        String password = "testpassword";
        AuthenticationRequestDTO request = new AuthenticationRequestDTO(email, password,username);


        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful!"))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.refresh_token").isNotEmpty())
                .andReturn();

    }

    @Test
    public void testEmailNotExist() throws Exception {

        String email = "emailnotexist@gmail.com";
        String username = "test";
        String password = "test";
        AuthenticationRequestDTO request = new AuthenticationRequestDTO(email, password,username);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Email or password does not match!"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.access_token").isEmpty())
                .andExpect(jsonPath("$.refresh_token").isEmpty())
                .andReturn();
    }

    @Test
    public void testPasswordNotMatch() throws Exception {

        String email = "test@gmail.com";
        String username = "";
        String password = "passworderror";
        AuthenticationRequestDTO request = new AuthenticationRequestDTO(email, password,username);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Email or password does not match!"))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.access_token").isEmpty())
                .andExpect(jsonPath("$.refresh_token").isEmpty())
                .andReturn();
    }
}