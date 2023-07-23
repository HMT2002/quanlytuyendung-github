package com.java08.quanlituyendung.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java08.quanlituyendung.dto.ProfileUpdateRequestDTO;
import com.java08.quanlituyendung.entity.Gender;
import com.java08.quanlituyendung.utils.Constant;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Transactional
class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper;
//    static String token;
    @BeforeEach
    void setUp() throws Exception {
            this.objectMapper = new ObjectMapper();
//            RegisterRequestDTO requestDTO = new RegisterRequestDTO();
//            requestDTO.setUsername("test1");
//            requestDTO.setPassword("testpassword");
//            requestDTO.setEmail("test1@gmail.com");
//            MvcResult result = mockMvc.perform(post("/auth/register")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(objectMapper.writeValueAsString(requestDTO)))
//                    .andExpect(status().isCreated())
//                    .andExpect(content().json("{\"message\": \"Registration successful!\"}"))
//                    .andReturn();
//            token = login(mockMvc,"test1@gmail.com","testpassword");
    }

    private String login(MockMvc mockMvc, String email, String password) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", "fooClientIdPassword");
        String requestBody = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
        ResultActions result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("fooClientIdPassword", "secret"))
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }

    @Test
    void getprofile() throws Exception {
        String token = login(mockMvc,"nguyenkhanh2kpiREC@gmail.com","1234");
        MvcResult result = mockMvc.perform(get("/profile")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value(Constant.SUCCESS))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andReturn();
    }

    @Test
    void update()  throws Exception {
        ProfileUpdateRequestDTO profileUpdateRequestDTO = new ProfileUpdateRequestDTO();
        profileUpdateRequestDTO.setFullName("Nguyen Le Khanh");
        profileUpdateRequestDTO.setEmail("nguyenkhanh2kpiREC@gmail.com");
        profileUpdateRequestDTO.setPhone("123456789");
        profileUpdateRequestDTO.setGender(Gender.MALE);
        profileUpdateRequestDTO.setAddress("567 Street, City");
        profileUpdateRequestDTO.setDob("10/2/2002");
        String token = login(mockMvc,"nguyenkhanh2kpiREC@gmail.com","1234");
        MvcResult result = mockMvc.perform(put("/profile")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(profileUpdateRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value(Constant.UPDATE_PROFILE_SUCCESS))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andReturn();
    }


}