package com.java08.quanlituyendung.converter;

import com.java08.quanlituyendung.dto.PositionRequestDTO;
import com.java08.quanlituyendung.entity.JobPostingEntity;
import com.java08.quanlituyendung.entity.PositionEntity;
import com.java08.quanlituyendung.entity.QuestionEntity;
import com.java08.quanlituyendung.entity.SkillsEntity;
import com.java08.quanlituyendung.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PositionConverter {

    @Autowired
    private final PositionRepository positionRepository;

    @Autowired
    private final QuestionRepository questionRepository;

    @Autowired
    private final SkillRepository skillRepository;

    @Autowired
    private final JobPostingRepository jobPostingRepository;

    @Autowired
    private final FieldRepository fieldRepository;

    public PositionEntity toEntity(PositionRequestDTO requestDTO) {
        PositionEntity entity = new PositionEntity();
        var field = fieldRepository.findById(requestDTO.getFieldID()).orElse(null);
        entity.setField(field);
        entity.setPositionName(requestDTO.getPositionName());

        List<QuestionEntity> lQuestionEntities = new ArrayList<QuestionEntity>();
        List<SkillsEntity> lSkillEntities = new ArrayList<SkillsEntity>();
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
        for (Long skillsId : requestDTO.getSkillEntities()) {

            var skillsEntity = skillRepository.findById(skillsId).orElse(null);
            if (skillsEntity == null) {
                System.out.println("null skill entityID: " + skillsId.toString());
            } else {
                lSkillEntities.add(skillsEntity);

            }
            System.out.println(skillsId);

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

        entity.setQuestionEntities(lQuestionEntities);
//        entity.setJobPostingEntities(lJobPostingEntities);
        entity.setSkillsEntities(lSkillEntities);

        return entity;
    }

    public PositionEntity toEntityById(int ID) {
        PositionEntity entity = positionRepository.findById(ID);
        if (entity != null) {
            return entity;

        }
        return null;

    }

    
    public PositionEntity toEntityByIdAndUpdate(long ID,PositionRequestDTO requestDTO) {

        PositionEntity entity = positionRepository.findById(ID);

        var field = fieldRepository.findById(requestDTO.getFieldID()).orElse(null);
        entity.setField(field);
        entity.setPositionName(requestDTO.getPositionName());

        List<QuestionEntity> lQuestionEntities = new ArrayList<QuestionEntity>();
        List<SkillsEntity> lSkillEntities = new ArrayList<SkillsEntity>();
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
        for (Long skillsId : requestDTO.getSkillEntities()) {

            var skillsEntity = skillRepository.findById(skillsId).orElse(null);
            if (skillsEntity == null) {
                System.out.println("null skill entityID: " + skillsId.toString());
            } else {
                lSkillEntities.add(skillsEntity);

            }
            System.out.println(skillsId);

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

        entity.setQuestionEntities(lQuestionEntities);
//        entity.setJobPostingEntities(lJobPostingEntities);
        entity.setSkillsEntities(lSkillEntities);

        return entity;

    }
}
