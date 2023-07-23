package com.java08.quanlituyendung.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java08.quanlituyendung.dto.ProvinceDTO;
import com.java08.quanlituyendung.dto.RegisterRequestDTO;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@Transactional
class ProvinceControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
//    static String token;
    @BeforeEach
    public void setUp() throws Exception {
//        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
//        requestDTO.setUsername("test1");
//        requestDTO.setPassword("testpassword");
//        requestDTO.setEmail("test1@gmail.com");
//        MvcResult result = mockMvc.perform(post("/auth/register")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(requestDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(content().json("{\"message\": \"Registration successful!\"}"))
//                .andReturn();
//        token = login(mockMvc,"test1@gmail.com","testpassword");
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
    //@Test
    void getProvinceById() throws Exception {
        String token = login(mockMvc,"nguyenkhanh2kpiREC@gmail.com","1234");
        MvcResult result = mockMvc.perform(get("/province/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.provinceName").exists())
                .andReturn();
    }

    //@Test
    void addProvince() throws Exception {
        ProvinceDTO provinceDTO = new ProvinceDTO();
        provinceDTO.setProvinceName("New Province");
        String token = login(mockMvc,"nguyenkhanh2kpiREC@gmail.com","1234");
        MvcResult result = mockMvc.perform(post("/province")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(provinceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.provinceName").exists())
                .andReturn();
    }

    //@Test
    void updateProvince() throws Exception {
        ProvinceDTO provinceDTO = new ProvinceDTO();
        provinceDTO.setId(1L);
        provinceDTO.setProvinceName("Lam DOng");
        String token = login(mockMvc,"nguyenkhanh2kpiREC@gmail.com","1234");
        MvcResult result = mockMvc.perform(put("/province/1")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(provinceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.provinceName").exists())
                .andReturn();
    }

    //@Test
    void deleteProvince() throws Exception {
        Long[] idList = new Long[]{1L, 2L};
        String token = login(mockMvc,"nguyenkhanh2kpiREC@gmail.com","1234");
        MvcResult result = mockMvc.perform(delete("/province")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(idList)))
                .andExpect(status().isOk())
                .andReturn();
    }


}