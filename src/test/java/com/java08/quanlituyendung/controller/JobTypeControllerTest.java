package com.java08.quanlituyendung.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java08.quanlituyendung.dto.JobTypeDTO;
import com.java08.quanlituyendung.service.IJobTypeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class JobTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IJobTypeService jobTypeService;


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
    public void testGetAllJobType() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        // Tạo danh sách JobTypeDTO để trả về từ service
        List<JobTypeDTO> jobTypeList = new ArrayList<>();
        // Thêm các JobTypeDTO vào danh sách

        // Thiết lập behavior cho jobTypeService.getAllJobType()
        when(jobTypeService.getAllJobType()).thenReturn(jobTypeList);

        // Gửi yêu cầu GET /job-type/get-all và kiểm tra phản hồi

        mockMvc.perform(get("/job-type")
                .header("authorization","Bearer "+ tk)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(jobTypeList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(jobTypeList.size())));
    }

    @Test
    public void testGetJobTypeById() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        Long jobId = 5L;
        JobTypeDTO jobTypeDTO = new JobTypeDTO();
        jobTypeDTO.setId(jobId);
        // Thiết lập behavior cho jobTypeService.getJobTypeById(jobId)
        when(jobTypeService.getJobTypeById(jobId)).thenReturn(jobTypeDTO);

        // Gửi yêu cầu GET /job-type/get-one/1 và kiểm tra phản hồi
        mockMvc.perform(get("/job-type/{id}",jobId)
                        .header("authorization","Bearer "+ tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jobTypeDTO)))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.id", equalTo(jobId.intValue())));
    }

    @Test
    public void testAddJobType() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        JobTypeDTO jobTypeDTO = new JobTypeDTO();
        String jobName = "DEV";
        jobTypeDTO.setJobName(jobName);
        // Thiết lập behavior cho jobTypeService.addJobType(jobTypeDTO)
        when(jobTypeService.addJobType(jobTypeDTO)).thenReturn(jobTypeDTO);

        // Gửi yêu cầu POST /job-type/create và kiểm tra phản hồi
        mockMvc.perform(post("/job-type")
                        .header("authorization","Bearer "+ tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jobTypeDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobName", equalTo(jobName)));
    }


    @Test
    public void testUpdateJobType() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        Long jobId = 18L;
        String jobName = "DEV1000";
        JobTypeDTO jobTypeDTO = new JobTypeDTO();
        jobTypeDTO.setId(jobId);
        jobTypeDTO.setJobName(jobName);
        // Thiết lập behavior cho jobTypeService.updateJobType(jobId, jobTypeDTO)
        when(jobTypeService.updateJobType(jobId, jobTypeDTO)).thenReturn(jobTypeDTO);

        // Gửi yêu cầu PUT /job-type/update/1 và kiểm tra phản hồi
        mockMvc.perform(put("/job-type/{id}", jobId)
                        .header("authorization","Bearer "+ tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(jobTypeDTO)))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.id", equalTo(jobId.intValue())))
                .andExpect(jsonPath("$.jobName", equalTo(jobName)));
    }

    @Test
    public void testDeleteJobType() throws Exception {
        String tk = login(mockMvc,"test@gmail.com", "testpassword");
        Long[] idArray = new Long[]{1L, 2L, 3L};
        doNothing().when(jobTypeService).delete(eq(idArray));
        mockMvc.perform(delete("/job-posting")
                        .header("authorization","Bearer "+ tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(idArray)))
                .andExpect(status().isOk());
    }
}
