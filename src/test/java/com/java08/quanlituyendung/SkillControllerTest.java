package com.java08.quanlituyendung;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java08.quanlituyendung.controller.SkillController;
import com.java08.quanlituyendung.dto.QuestionDTO;
import com.java08.quanlituyendung.dto.RegisterRequestDTO;
import com.java08.quanlituyendung.dto.ResponseDTO;
import com.java08.quanlituyendung.dto.SkillRequestDTO;
import com.java08.quanlituyendung.service.IPositionService;

import java.util.ArrayList;
import java.util.List;


import com.java08.quanlituyendung.service.IQuestionService;
import com.java08.quanlituyendung.service.ISkillService;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class SkillControllerTest {

    @MockBean
    private ISkillService skillService;
    @Autowired
    private MockMvc mockMvc;

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
    public void testCreateSkill() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        SkillRequestDTO skillRequest = new SkillRequestDTO();
        skillRequest.setSkillName("long");

        ResponseDTO responseDTO = new ResponseDTO();
        when(skillService.createSkill(skillRequest)).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/skill")
                        .header("authorization","Bearer "+ tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(skillRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(responseDTO)));

    }

    //@Test
    public void testGetSkill() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        SkillRequestDTO skillRequest = new SkillRequestDTO();
        skillRequest.setSkillName("Java");

        ResponseDTO responseDTO = new ResponseDTO();
        when(skillService.getSkill(skillRequest)).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/skill")
                        .header("authorization","Bearer "+ tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(skillRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(responseDTO)));

    }

    //@Test
    public void testUpdateSkill() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        long id = 1L;
        SkillRequestDTO skillRequest = new SkillRequestDTO();
        skillRequest.setID(id);
        skillRequest.setSkillName("What is your name?");
        ResponseDTO responseDTO = new ResponseDTO();
        when(skillService.updateSkill(id,skillRequest)).thenReturn(responseDTO);

        mockMvc.perform(put("/skill/{id}", id)
                        .header("authorization","Bearer "+ tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(skillRequest)))
                .andExpect(status().isOk());

    }

    //@Test
    public void testDeleteSkill() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        SkillRequestDTO skillRequest = new SkillRequestDTO();
        skillRequest.setSkillName("Java");
        List<Long> ids =new  ArrayList<Long>() ;
        ids.add(1L);
        ids.add(2L);
        ids.add(3L);

        skillRequest.setDeleteIds(ids);

        ResponseDTO responseDTO = new ResponseDTO();
        when(skillService.deleteSkill(skillRequest)).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.delete("/skill/delete")
                        .header("authorization","Bearer "+ tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ids)))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
