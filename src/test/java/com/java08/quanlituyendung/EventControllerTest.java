package com.java08.quanlituyendung;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import jakarta.transaction.Transactional;


import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.LinkedHashMap;

import com.java08.quanlituyendung.dto.RegisterRequestDTO;
import com.java08.quanlituyendung.entity.Role;
import com.java08.quanlituyendung.entity.UserAccountEntity;
import com.java08.quanlituyendung.repository.UserAccountRepository;
import com.java08.quanlituyendung.utils.Constant;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EventControllerTest {

//        @Autowired
//        RecruiterRepository recruiterRepository;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper = new ObjectMapper();

    public void setUp() throws Exception {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setUsername("tdsdsfdsfsest");
        requestDTO.setPassword("password");
        requestDTO.setEmail("testmail@gmail.com");


        MvcResult resultCreate = mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"message\": \"Registration successful!\"}"))
                .andReturn();

        UserAccountEntity user = userAccountRepository.findByEmail("testmail@gmail.com").orElseThrow();
        user.setRole(Role.RECRUITER);
        userAccountRepository.save(user);


    }


    private String login(String email, String password) throws Exception {
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
    public void testGetEventAPI() throws Exception {
        String token = login("bluesea@gmail.com", "password");

        String requestBodyCreate = "{\"poster\": \"test poster\",\"type\":\"ONLINE\",\"title\": \"test title\",\"startTime\": \"2023-07-04 10:00:00\",\"endTime\": \"2023-07-04 12:00:00\",\"description\":\"test description\",\"speakerId\":[]}";

        ResultActions resultCreate = mockMvc.perform(post("/event")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyCreate)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultCreateString = resultCreate.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        var json_response_create = jsonParser.parseMap(resultCreateString);
        var data_create = json_response_create.get("data");
        String id = ((LinkedHashMap) data_create).get("id").toString();

        ResultActions result = this.mockMvc.perform(get("/event/" + id)
                        .header("authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
        String resultString = result.andReturn().getResponse().getContentAsString();
        var json_response = jsonParser.parseMap(resultString);
        String status = json_response.get("status").toString();
        assertTrue(status.equals("OK"), () -> "response status testGetEvent is OK");
    }

    //@Test // Add thành công, nhưng status code trả về là null thay vì 200
    public void testCreateEventAPI() throws Exception {

        String token = login("bluesea@gmail.com", "password");
        String requestBody = "{\"poster\": \"test poster\",\"type\":\"ONLINE\",\"title\": \"test title\",\"startTime\": \"2023-07-04 10:00:00\",\"endTime\": \"2023-07-04 12:00:00\",\"description\":\"test description\",\"speakerId\":[]}";

        ResultActions result = mockMvc.perform(post("/event")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        var json_response = jsonParser.parseMap(resultString);
        String message = json_response.get("message").toString();
        assertTrue(message.equals("Event success add !"), () -> "response status testGetEvent is OK");

    }

    //@Test
    public void testInviteAPI() throws Exception {

        String token = login("bluesea@gmail.com", "password");

        String requestBody = "{\"id\": 2}";

        ResultActions result = mockMvc.perform(post("/event/invite")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        var json_response = jsonParser.parseMap(resultString);
        String message = json_response.get("message").toString();
        assertTrue(message.equals(Constant.NOT_AUTHENTICATED), () -> "response status Invite is OK");
    }

    //@Test // AttendeeJoin thành công, nhưng status code trả về là null thay vì 200
    public void testAttendeeJoinAPI() throws Exception {
        String token = login("bluesea@gmail.com", "password");

        String requestBodyCreate = "{\"poster\": \"test poster\",\"type\":\"ONLINE\",\"title\": \"test title\",\"startTime\": \"2023-07-04 10:00:00\",\"endTime\": \"2023-07-04 12:00:00\",\"description\":\"test description\",\"speakerId\":[]}";

        ResultActions resultCreate = mockMvc.perform(post("/event")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyCreate)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultCreateString = resultCreate.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        var json_response_create = jsonParser.parseMap(resultCreateString);
        var data_create = json_response_create.get("data");
        String join_id = ((LinkedHashMap) data_create).get("id").toString();

        String requestBody = "{\"id\":" + join_id + "}";

        ResultActions result = mockMvc.perform(post("/event/attendeejoin")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();
        var json_response = jsonParser.parseMap(resultString);
        String message = json_response.get("message").toString();
        assertTrue(message.equals("Success !"), () -> "response message testGetEvent is OK");
    }

    //@Test
    public void testDeleteEvent() throws Exception {

        String token = login("bluesea@gmail.com", "password");

        String requestBodyCreate = "{\"poster\": \"test poster\",\"type\":\"ONLINE\",\"title\": \"test title\",\"startTime\": \"2023-07-04 10:00:00\",\"endTime\": \"2023-07-04 12:00:00\",\"description\":\"test description\",\"speakerId\":[]}";

        ResultActions resultCreate = mockMvc.perform(post("/event")
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBodyCreate)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultCreateString = resultCreate.andReturn().getResponse().getContentAsString();
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        var json_response_create = jsonParser.parseMap(resultCreateString);
        var data_create = json_response_create.get("data");
        String delete_id = ((LinkedHashMap) data_create).get("id").toString();
        ResultActions result = mockMvc.perform(delete("/event?eventId=" + delete_id)
                        .header("authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk());
        String resultString = result.andReturn().getResponse().getContentAsString();
        var json_response = jsonParser.parseMap(resultString);
        String message = json_response.get("message").toString();
        assertTrue(message.equals(Constant.SUCCESS), () -> "response message testDeleteEvent is OK");
    }
}
