package com.java08.quanlituyendung.service;

import com.java08.quanlituyendung.dto.InterviewToolDTO;

import java.util.List;

public interface IInterviewToolService {
    public List<InterviewToolDTO> getAll();

    public InterviewToolDTO addTool(InterviewToolDTO tool);

    public Boolean removeToolById(Long id);

    public InterviewToolDTO getToolById(Long id);

    public InterviewToolDTO updateToolById(InterviewToolDTO tool, Long id);
}
