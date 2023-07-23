package com.java08.quanlituyendung.converter;

import org.springframework.stereotype.Component;

import com.java08.quanlituyendung.dto.InterviewToolDTO;
import com.java08.quanlituyendung.entity.InterviewToolEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InterviewToolConverter {
    public InterviewToolEntity toEntity(InterviewToolDTO dto) {
        InterviewToolEntity tool = new InterviewToolEntity();

        tool.setId(dto.getId());
        tool.setName(dto.getName());
        tool.setWebsite(dto.getWebsite());

        return tool;
    }

    public InterviewToolDTO toDto(InterviewToolEntity tool) {
        InterviewToolDTO dto = new InterviewToolDTO();

        dto.setId(tool.getId());
        dto.setName(tool.getName());
        dto.setWebsite(tool.getWebsite());

        return dto;
    }
}
