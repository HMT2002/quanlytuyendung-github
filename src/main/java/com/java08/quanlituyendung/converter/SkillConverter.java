package com.java08.quanlituyendung.converter;

import com.java08.quanlituyendung.dto.PositionRequestDTO;
import com.java08.quanlituyendung.dto.SkillRequestDTO;
import com.java08.quanlituyendung.entity.FieldEntity;
import com.java08.quanlituyendung.entity.JobPostingEntity;
import com.java08.quanlituyendung.entity.PositionEntity;
import com.java08.quanlituyendung.entity.QuestionEntity;
import com.java08.quanlituyendung.entity.SkillsEntity;
import com.java08.quanlituyendung.repository.FieldRepository;
import com.java08.quanlituyendung.repository.JobPostingRepository;
import com.java08.quanlituyendung.repository.PositionRepository;
import com.java08.quanlituyendung.repository.QuestionRepository;
import com.java08.quanlituyendung.repository.SkillRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SkillConverter {

    @Autowired
    private final QuestionRepository questionRepository;

    @Autowired
    private final SkillRepository skillRepository;

    @Autowired
    private final JobPostingRepository jobPostingRepository;

    @Autowired
    private final FieldRepository fieldRepository;

    public SkillsEntity toEntity(SkillRequestDTO requestDTO) {
        SkillsEntity entity = new SkillsEntity();
        return entity;
    }

        public SkillsEntity toEntityById(int ID) {
        SkillsEntity entity = skillRepository.findById(ID);
        if(entity!=null){
        return entity;

        }
            return null;
        
    }

        public SkillsEntity toEntityByIdAndUpdate(long ID,SkillRequestDTO requestDTO) {
        SkillsEntity entity = skillRepository.findById(ID);
        entity.setSkillName(requestDTO.getSkillName());
        List<QuestionEntity> lQuestionEntities = new ArrayList<QuestionEntity>();
        List<JobPostingEntity> lJobPostingEntities = new ArrayList<JobPostingEntity>();

        for (Long jobPostingId : requestDTO.getJobPostingEntities()) {
            var jobPostingEntity = jobPostingRepository.findById(jobPostingId).orElse(null);
            if (jobPostingEntity == null) {
                System.out.println("null jobposting entityID: " + jobPostingEntity.toString());
            } else {
                lJobPostingEntities.add(jobPostingEntity);

            }
            System.out.println(jobPostingEntity);

        }
        for (Long questionId : requestDTO.getQuestionEntities()) {
            var questionEntity = questionRepository.findById(questionId).orElse(null);
            if (questionEntity == null) {
                System.out.println("null question entityID: " + questionId.toString());
            } else {
                lQuestionEntities.add(questionEntity);

            }
            System.out.println(questionEntity);

        }
        entity.setQuestions(lQuestionEntities);
        entity.setJobPostings(lJobPostingEntities);

        return entity;

    }
}
