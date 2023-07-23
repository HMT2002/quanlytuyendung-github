package com.java08.quanlituyendung.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;

@Entity
@Table(name = "Question")
public class QuestionEntity extends BaseEntity {
    @Column(name = "question")
    private String question;

    @Column(name = "isDelete")
    private boolean isDelete;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fieldId")
    private FieldEntity field;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "positionId")
    private PositionEntity positionEntity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobTypeId")
    private JobTypeEntity jobType;

    @ManyToMany
    @JoinTable(name = "Skills_Question", joinColumns = @JoinColumn(name = "questionId"), inverseJoinColumns = @JoinColumn(name = "skillId"))
    private List<SkillsEntity> skillsEntities = new ArrayList<>();

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public FieldEntity getField() {
        return field;
    }

    public void setField(FieldEntity field) {
        this.field = field;
    }

    public PositionEntity getPositionEntity() {
        return positionEntity;
    }

    public void setPositionEntity(PositionEntity positionEntity) {
        this.positionEntity = positionEntity;
    }

    public JobTypeEntity getJobType() {
        return jobType;
    }

    public void setJobType(JobTypeEntity jobType) {
        this.jobType = jobType;
    }

    public List<SkillsEntity> getSkillsEntities() {
        return skillsEntities;
    }

    public void setSkillsEntities(List<SkillsEntity> skillsEntities) {
        this.skillsEntities = skillsEntities;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
