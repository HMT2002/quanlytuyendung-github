package com.java08.quanlituyendung;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java08.quanlituyendung.controller.QuestionController;
import com.java08.quanlituyendung.dto.QuestionDTO;
import com.java08.quanlituyendung.dto.RegisterRequestDTO;
import com.java08.quanlituyendung.service.IQuestionService;

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
import org.springframework.http.MediaType;
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

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
 public class QuestionControllerTest {

    @MockBean
    private IQuestionService questionService;
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
    public void testCreateQuestion() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        QuestionDTO question = new QuestionDTO();
        question.setQuestion("What is your name?");
        when(questionService.save(any(QuestionDTO.class))).thenReturn(question);
        mockMvc.perform(post("/question")
                        .header("authorization","Bearer "+ tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(question)))
                .andExpect(status().isOk());
    }
    //@Test
    public void testCreateQuestionWrongTypeValue() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        String invalidJsonContent = "{\n" +
                "  \"id\": \"string\",\n" +
                "  \"question\": \"string\",\n" +
                "  \"questionText\": \"string\"\n" +
                "}";
        mockMvc.perform(post("/question")
                        .header("authorization","Bearer "+ tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJsonContent))
                .andExpect(status().isBadRequest());
    }


    //@Test
    public void testGetAllQuestion() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        QuestionDTO question1 = new QuestionDTO();
        question1.setId(1L);
        question1.setQuestion("What is your name?");

        QuestionDTO question2 = new QuestionDTO();
        question2.setId(2L);
        question2.setQuestion("How old are you?");

        List<QuestionDTO> questionList = Arrays.asList(question1, question2);
        when(questionService.getAllQuestion()).thenReturn(questionList);

        mockMvc.perform(get("/question").header("authorization","Bearer "+ tk))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].question", is("What is your name?")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].question", is("How old are you?")));

    }

    //@Test
    public void testDelete() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        Long[] ids = {1L, 2L, 3L};

        mockMvc.perform(delete("/question")
                        .header("authorization","Bearer "+ tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(ids)))
                .andExpect(status().isOk())
                .andExpect(content().string("Delete Success!"));
    }

    //@Test
    public void testUpdateQuestion() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        long id = 1L;
        QuestionDTO question = new QuestionDTO();
        question.setId(id);
        question.setQuestion("What is your name?");
        when(questionService.updateQuestion(any(QuestionDTO.class))).thenReturn(question);

        mockMvc.perform(put("/question/{id}", id)
                        .header("authorization","Bearer "+ tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(question)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.question", is("What is your name?")));

    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


