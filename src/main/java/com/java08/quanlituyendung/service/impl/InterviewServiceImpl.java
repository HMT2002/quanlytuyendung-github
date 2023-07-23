package com.java08.quanlituyendung.service.impl;

import com.java08.quanlituyendung.converter.InterviewConverter;
import com.java08.quanlituyendung.dto.InterviewDTO;
import com.java08.quanlituyendung.entity.InterviewEntity;
import com.java08.quanlituyendung.repository.InterviewRepository;
import com.java08.quanlituyendung.service.IInterviewService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class InterviewServiceImpl implements IInterviewService {
    private final InterviewRepository interviewRepository;
    private final InterviewConverter converter;

    public InterviewServiceImpl(InterviewRepository interviewRepository, InterviewConverter converter) {
        this.interviewRepository = interviewRepository;
        this.converter = converter;
    }

    @Override
    public List<InterviewDTO> getAll() {
        List<InterviewDTO> list = new ArrayList<>();
        for (InterviewEntity interview : interviewRepository.findAll()) {
            list.add(converter.toDto(interview));
        }

        return list;
    }

    @Override
    public InterviewDTO addInterview(InterviewDTO interview) {
        return converter.toDto(interviewRepository.save(converter.toEntity(interview)));
    }

    @Override
    public Boolean removeInterview(Long id) {
        if (hasInterview(id)) {
            interviewRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean hasInterview(Long id) {
        return interviewRepository.existsById(id);
    }

    @Override
    public InterviewDTO getInterviewById(Long id) {
        Optional<InterviewEntity> entity = interviewRepository.findById(id);

        return entity.map(converter::toDto).orElse(null);

    }

    public InterviewDTO updateInterview(InterviewDTO dto, Long id, Boolean overwrite) {
        if (overwrite) {
            InterviewEntity interview = converter.toEntity(dto);
            interview.setId(id);
            return converter.toDto(interviewRepository.save(interview));
        } else {
            Optional<InterviewEntity> entity = interviewRepository.findById(id);

            if (entity.isEmpty())
                return null;

            InterviewDTO oldDto = converter.toDto(entity.get());


            if (dto.getDatetime() != null && !Objects.equals(oldDto.getDatetime(), dto.getDatetime()))
                oldDto.setDatetime(dto.getDatetime());

            if (dto.getNote() != null && !Objects.equals(oldDto.getNote(), dto.getNote()))
                oldDto.setNote(dto.getNote());

            if (dto.getScore() != null && !Objects.equals(oldDto.getScore(), dto.getScore()))
                oldDto.setScore(dto.getScore());

            if (dto.getCandidateId() != null && !Objects.equals(oldDto.getCandidateId(), dto.getCandidateId()))
                oldDto.setCandidateId(dto.getCandidateId());

            if (dto.getInterviewerIds() != null && !Objects.equals(oldDto.getInterviewerIds(), dto.getInterviewerIds()))
                oldDto.setInterviewerIds(dto.getInterviewerIds());

            if (dto.getJobPostId() != null && !Objects.equals(oldDto.getInterviewerIds(), dto.getInterviewerIds()))
                oldDto.setJobPostId(dto.getJobPostId());

            return converter.toDto(interviewRepository.save(converter.toEntity(oldDto)));
        }
    }
}
