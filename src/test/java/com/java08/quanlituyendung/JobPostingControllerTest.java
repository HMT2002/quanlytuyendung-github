//package com.java08.quanlituyendung;
//
//import com.java08.quanlituyendung.dto.JobPostingDTO;
//import com.java08.quanlituyendung.service.IJobPostingService;
//import org.junit.jupiter.api.Test;
//
//import static org.hamcrest.Matchers.equalTo;
//import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
//import static org.junit.jupiter.api.Assertions.*;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import com.fasterxml.jackson.core.type.TypeReference;
//import org.springframework.test.web.servlet.ResultMatcher;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.springframework.boot.json.JacksonJsonParser;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//class JobPostingControllerTest {
//
//    @Autowired private MockMvc mockMvc;
//    @MockBean private IJobPostingService jobPostingService;
//
//    private String login(MockMvc mockMvc, String email, String password) throws Exception {
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "password");
//        params.add("client_id", "fooClientIdPassword");
//
//        String requestBody = "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
//
//        ResultActions result = mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestBody)
//                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("fooClientIdPassword", "secret"))
//                        .accept("application/json;charset=UTF-8"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"));
//
//        String resultString = result.andReturn().getResponse().getContentAsString();
//
//        JacksonJsonParser jsonParser = new JacksonJsonParser();
//        return jsonParser.parseMap(resultString).get("access_token").toString();
//    }
//
//    //@Test
//    public void testGetAllJobPosting() throws Exception {
//        String tk = login(mockMvc,"test@gmail.com", "testpassword");
//        List<JobPostingDTO> jobPostingList = new ArrayList<>();
//        when(jobPostingService.getAllJobPosting()).thenReturn(jobPostingList);
//        mockMvc.perform(get("/job-posting")
//                .header("authorization","Bearer "+ tk))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) jsonPath("$", hasSize(jobPostingList.size())));
//    }
//
//    //@Test
//    public void testCreateJobPosting() throws Exception {
//        String tk = login(mockMvc,"test@gmail.com", "testpassword");
//        JobPostingDTO jobPostingDTO = new JobPostingDTO();
//        when(jobPostingService.save(jobPostingDTO)).thenReturn(jobPostingDTO);
//
//        MvcResult result = mockMvc.perform(post("/job-posting")
//                        .header("authorization","Bearer "+ tk)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(jobPostingDTO)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseBody = result.getResponse().getContentAsString();
//        JobPostingDTO responseDTO = new ObjectMapper().readValue(responseBody, JobPostingDTO.class);
//
//        assertEquals(jobPostingDTO, responseDTO);
//    }
//
//    //@Test
//    public void testUpdateJobPosting() throws Exception {
//        String tk = login(mockMvc,"test@gmail.com", "testpassword");
//        Long jobPostingId = 100L;
//        JobPostingDTO jobPostingDTO = new JobPostingDTO();
//        jobPostingDTO.setId(jobPostingId);
//        when(jobPostingService.save(jobPostingDTO)).thenReturn(jobPostingDTO);
//        mockMvc.perform(put("/job-posting/{id}", jobPostingId)
//                        .header("authorization","Bearer "+ tk)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(jobPostingDTO)))
//                .andExpect(status().isOk())
//                .andExpect((ResultMatcher) jsonPath("$.id", equalTo(jobPostingId.intValue())));
//    }
//
//    //@Test
//    public void testGetAllJobPosting_ReturnsJobPostingList() throws Exception {
//        String tk = login(mockMvc,"test@gmail.com", "testpassword");
//        List<JobPostingDTO> jobPostingList = new ArrayList<>();
//        jobPostingList.add(new JobPostingDTO(1, "Job 1", "", "", "", "", false));
//        jobPostingList.add(new JobPostingDTO(2, "Job 2", "", "", "", "", false));
//
//        when(jobPostingService.getAllJobPosting()).thenReturn(jobPostingList);
//
//        MvcResult result = mockMvc.perform(get("/job-posting")
//                .header("authorization","Bearer "+ tk))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        String responseBody = result.getResponse().getContentAsString();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<JobPostingDTO> responseList = objectMapper.readValue(responseBody, new TypeReference<>() {
//        });
//
//        assertNotNull(responseList);
//        assertEquals(2, responseList.size());
//
//        assertEquals(1, responseList.get(0).getNumber());
//        assertEquals("Job 1", responseList.get(0).getDescription());
//        assertEquals(2, responseList.get(1).getNumber());
//        assertEquals("Job 2", responseList.get(1).getDescription());
//    }
//
//    //@Test
//    public void testDeleteJobPosting() throws Exception {
//        String tk = login(mockMvc,"test@gmail.com", "testpassword");
//        Long[] idArray = new Long[]{1L, 2L, 3L};
//        doNothing().when(jobPostingService).delete(eq(idArray));
//
//        mockMvc.perform(delete("/job-posting")
//                        .header("authorization","Bearer "+ tk)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(idArray)))
//                .andExpect(status().isOk());
//    }
//}
