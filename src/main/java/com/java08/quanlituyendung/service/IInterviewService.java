package com.java08.quanlituyendung.service;

import com.java08.quanlituyendung.dto.InterviewDTO;

import java.util.List;

public interface IInterviewService {
    public List<InterviewDTO> getAll();

    public InterviewDTO addInterview(InterviewDTO interview);

    public Boolean removeInterview(Long id);

    public Boolean hasInterview(Long id);

    public InterviewDTO getInterviewById(Long id);

    public InterviewDTO updateInterview(InterviewDTO interview, Long id, Boolean overwrite);
}


