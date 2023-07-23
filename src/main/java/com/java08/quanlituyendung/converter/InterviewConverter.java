package com.java08.quanlituyendung.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.java08.quanlituyendung.dto.InterviewDTO;
import com.java08.quanlituyendung.entity.InterviewEntity;
import com.java08.quanlituyendung.entity.InterviewToolEntity;
import com.java08.quanlituyendung.entity.JobPostingEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InterviewConverter {
    public InterviewDTO toDto(InterviewEntity interview) {
        InterviewDTO dto = new InterviewDTO();

        dto.setId(interview.getId());

//        if (interview.getCandidateEntity() != null)
//            dto.setCandidateId(interview.getCandidateEntity().getId());
//
//        if (interview.getJobPostingEntity() != null)
//            dto.setJobPostId(interview.getJobPostingEntity().getId());
//
//        if (interview.getInterviewToolEntity() != null)
//            dto.setToolId(interview.getInterviewToolEntity().getId());
//
//        if (interview.getInterviewerEntities() != null) {
//            List<Long> interviewerIds = new ArrayList<>();
//            for (InterviewerEntity interviewerEntity : interview.getInterviewerEntities()) {
//                interviewerIds.add(interviewerEntity.getId());
//            }
//            dto.setInterviewerIds(interviewerIds);
//        }

        dto.setDatetime(interview.getDateTime());
        dto.setScore(interview.getScore());
        dto.setNote(interview.getNote());

        return dto;
    }

    public InterviewEntity toEntity(InterviewDTO dto) {
        InterviewEntity interview = new InterviewEntity();

        interview.setId(dto.getId());
        interview.setDateTime(dto.getDatetime());

        if (dto.getScore() != null)
            interview.setScore(dto.getScore());

        if (dto.getNote() != null)
            interview.setNote(dto.getNote());

        if (dto.getToolId() != null) {
            InterviewToolEntity toolEntity = new InterviewToolEntity();
            toolEntity.setId(dto.getToolId());
            interview.setInterviewToolEntity(toolEntity);
        }
//
//        if (dto.getCandidateId() != null) {
//            CandidateEntity candidate = new CandidateEntity();
//            candidate.setId(dto.getCandidateId());
//            interview.setCandidateEntity(candidate);
//        }
//
//        if (dto.getJobPostId() != null) {
//            JobPostingEntity jobPostingEntity = new JobPostingEntity();
//            jobPostingEntity.setId(dto.getJobPostId());
//            interview.setJobPostingEntity(jobPostingEntity);
//        }
//
//        if (dto.getInterviewerIds() != null) {
//            List<InterviewerEntity> interviewers = new ArrayList<>();
//            for (Long interviewerId : dto.getInterviewerIds()) {
//                InterviewerEntity interviewerEntity = new InterviewerEntity();
//                interviewerEntity.setId(interviewerId);
//                interviewers.add(interviewerEntity);
//            }
//            interview.setInterviewerEntities(interviewers);
//        }

        return interview;
    }
}
