package com.java08.quanlituyendung.service.impl;

import com.java08.quanlituyendung.converter.InterviewToolConverter;
import com.java08.quanlituyendung.dto.InterviewToolDTO;
import com.java08.quanlituyendung.entity.InterviewToolEntity;
import com.java08.quanlituyendung.repository.InterviewToolRepository;
import com.java08.quanlituyendung.service.IInterviewToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterviewToolServiceImpl implements IInterviewToolService {
    @Autowired
    private InterviewToolRepository repository;
    @Autowired
    private InterviewToolConverter converter;

    @Override
    public List<InterviewToolDTO> getAll() {
        List<InterviewToolDTO> list = new ArrayList<>();
        for (InterviewToolEntity tool : repository.findAll()) {
            list.add(converter.toDto(tool));
        }

        return list;
    }

    @Override
    public InterviewToolDTO addTool(InterviewToolDTO tool) {
        return converter.toDto(repository.save(converter.toEntity(tool)));
    }

    @Override
    public Boolean removeToolById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public InterviewToolDTO getToolById(Long id) {
        Optional<InterviewToolEntity> entity = repository.findById(id);

        if (entity.isEmpty())
            return null;

        return converter.toDto(entity.get());
    }

    @Override
    public InterviewToolDTO updateToolById(InterviewToolDTO dto, Long id) {
        Optional<InterviewToolEntity> entity = repository.findById(id);

        if (entity.isEmpty())
            return null;

        InterviewToolEntity tool = entity.get();

        if (dto.getName() != null)
            tool.setName(dto.getName());

        if (dto.getWebsite() != null)
            tool.setWebsite(dto.getWebsite());
        ;

        return converter.toDto(repository.save(tool));
    }
}
