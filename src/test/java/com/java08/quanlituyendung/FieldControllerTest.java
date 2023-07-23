package com.java08.quanlituyendung;

import com.java08.quanlituyendung.dto.FieldDTO;
import com.java08.quanlituyendung.service.IFieldService;
import com.java08.quanlituyendung.service.impl.FieldServiceImpl;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class FieldControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFieldService ifieldservice;

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
    public void testGetFields() throws Exception {
        // Given
        String tk = login(mockMvc,"tuanbmt202bmt@gmail.com", "tuanbmt123");
        FieldDTO field1 = new FieldDTO(1, "Toan");
        FieldDTO field2 = new FieldDTO(8, "Anh");
        List<FieldDTO> fields = Arrays.asList(field1, field2);

        when(ifieldservice.getAllField()).thenReturn(fields);
        mockMvc.perform(get("/field")
                 .header("authorization","Bearer "+ tk)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fieldName").value(is(field1.getFieldName())))
                .andExpect(jsonPath("$[1].fieldName").value(is(field2.getFieldName())));
    }

    //@Test
    public void testNumberOfFields() throws Exception {
        String tk = login(mockMvc,"tuanbmt202bmt@gmail.com", "tuanbmt123");
        FieldDTO field1t2 = new FieldDTO(1, "Toan");
        FieldDTO field2t2 = new FieldDTO(8, "Anh");
        FieldDTO field3t2 = new FieldDTO(13, "Tin");
        List<FieldDTO> fieldst2 = Arrays.asList(field1t2, field2t2, field3t2);
        when(ifieldservice.getAllField()).thenReturn(fieldst2);
        mockMvc.perform(get("/field")
                 .header("authorization","Bearer "+ tk)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fieldName").value(is(field1t2.getFieldName())))
                .andExpect(jsonPath("$[1].fieldName").value(is(field2t2.getFieldName())))
                .andExpect(jsonPath("$[2].fieldName").value(is(field3t2.getFieldName())))
                .andExpect(jsonPath("$", Matchers.hasSize(3)));
    }

    //@Test
    public void testUpdate() throws Exception {
        String tk = login(mockMvc,"tuanbmt202bmt@gmail.com", "tuanbmt123");
        FieldDTO field1t3 = new FieldDTO(1, "Toan");
        FieldDTO field2t3 = new FieldDTO(8, "Anh");
        FieldDTO field3t3 = new FieldDTO(1, "Tin");
        List<FieldDTO> fieldst3 = Arrays.asList(field1t3, field2t3);
        // arraylist of ints to store the ids of the fields
        List<Integer> ids = Arrays.asList(1, 8);
        when(ifieldservice.getAllField()).thenReturn(fieldst3);
        when(ifieldservice.update(any(FieldDTO.class))).thenAnswer(invocation -> {
            FieldDTO argument = invocation.getArgument(0);

            // Find the field with the same ID and update it
            for (int i = 0; i < fieldst3.size(); i++) {
                if (fieldst3.get(i).getId() == argument.getId()) {
                    fieldst3.set(i, argument);
                    return argument;
                }
            }

            // If no field with the given ID was found, throw an exception
            throw new IllegalArgumentException("No field with the given ID was found");
        });

        mockMvc.perform(get("/field")
                        .header("authorization","Bearer "+ tk)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fieldName").value(is(field1t3.getFieldName())))
                .andExpect(jsonPath("$[1].fieldName").value(is(field2t3.getFieldName())));

        mockMvc.perform(put("/field/1")
                .header("authorization","Bearer "+ tk)
                .contentType(MediaType.APPLICATION_JSON));
        ifieldservice.update(field3t3);
        mockMvc.perform(get("/field")
                        .header("authorization","Bearer "+ tk)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fieldName").value(is(field3t3.getFieldName())));
    }

}
