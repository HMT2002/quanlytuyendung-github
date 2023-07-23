package com.java08.quanlituyendung;

import com.java08.quanlituyendung.controller.FieldController;
import com.java08.quanlituyendung.controller.InterviewController;
import com.java08.quanlituyendung.dto.InterviewDTO;
import com.java08.quanlituyendung.service.IInterviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InterviewTests {

	@Autowired
	private FieldController fieldController;
	@Autowired
	private  InterviewController interviewController;
	private  MockMvc mockMvc;
	@MockBean
	private IInterviewService interviewService;

	@Test
	void contextLoads() {
	}
	@Test
	void testGetAllInterviews() {
		InterviewDTO interviewDTO = new InterviewDTO();
		List<InterviewDTO> interviewDTOList = new ArrayList<>();
		interviewDTOList.add(interviewDTO);
		InterviewController interviewController = Mockito.mock(InterviewController.class);
		Mockito.when(interviewController.getAllInterviews()).thenReturn(interviewDTOList);
		List<InterviewDTO> actualInterviews = interviewController.getAllInterviews();
		assertNotNull(actualInterviews);
		assertEquals(interviewDTOList.size(), actualInterviews.size());
	}
	@Test
	void testAddInterview() {
		// Arrange
		InterviewDTO interviewDTO = new InterviewDTO();
		InterviewController interviewController = Mockito.mock(InterviewController.class);
		Mockito.when(interviewController.addInterview(Mockito.any())).thenReturn(interviewDTO);
		interviewDTO.setCandidateId(1L);
		interviewDTO.setJobPostId(1L);
		interviewDTO.setToolId(1L);
		interviewDTO.setDatetime("20 -04-2002");
		interviewDTO.setScore(1);
		interviewDTO.setNote("note");
		InterviewDTO actualInterview = interviewController.addInterview(interviewDTO);
		assertNotNull(actualInterview);
		assertEquals(interviewDTO.getId(), actualInterview.getId());
	}




	//----------------------------------------------------


}
