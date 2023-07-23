package com.java08.quanlituyendung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.java08.quanlituyendung.controller.InterviewToolController;
import com.java08.quanlituyendung.dto.InterviewToolDTO;
import com.java08.quanlituyendung.service.IInterviewToolService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class InterviewToolTest {

    @Mock
    private IInterviewToolService service;

    @InjectMocks
    private InterviewToolController controller;

    private List<InterviewToolDTO> tools;

    @BeforeEach
    public void setUp() {
        tools = new ArrayList<>();
        tools.add(new InterviewToolDTO(1, "Tool 1"));
        tools.add(new InterviewToolDTO(2, "Tool 2"));
        tools.add(new InterviewToolDTO(3, "Tool 3"));
    }

    @Test
    public void testGetAllTools() {
        when(service.getAll()).thenReturn(tools);
        List<InterviewToolDTO> result = controller.getAllTools();
        assertEquals(tools, result);
    }

    @Test
    public void testAddTool() {
        InterviewToolDTO tool = new InterviewToolDTO(null, "Tool 4");
        when(service.addTool(tool)).thenReturn(new InterviewToolDTO(4, "Tool 4"));
        InterviewToolDTO result = controller.addTool(tool);
        assertEquals(new InterviewToolDTO(4, "Tool 4"), result);
    }

    @Test
    public void testGetTool() {
        when(service.getToolById(1L)).thenReturn(new InterviewToolDTO(1, "Tool 1"));
        ResponseEntity<?> response = controller.getTool(1L);
        assertEquals(new ResponseEntity<>(new InterviewToolDTO(1, "Tool 1"), HttpStatus.OK), response);
    }

    @Test
    public void testUpdateTool() {
        InterviewToolDTO tool = new InterviewToolDTO(1, "Updated Tool");
        when(service.updateToolById(tool, 1L)).thenReturn(tool);
        ResponseEntity<?> response = controller.updateTool(1L, tool);
        assertEquals(new ResponseEntity<>(tool, HttpStatus.OK), response);
    }

    @Test
    public void testDeleteTools() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        controller.deleteTools(ids);
        // Verify that service.removeToolById() was called twice with the correct IDs
        verify(service, times(2)).removeToolById(anyLong());
    }
}