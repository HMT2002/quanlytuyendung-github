package com.java08.quanlituyendung.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Position")
public class PositionEntity extends BaseEntity {
    @Column(name = "positionName")
    private String positionName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fieldId")
    private FieldEntity field;

    @ManyToMany
    @JoinTable(name = "Skills_Position", joinColumns = @JoinColumn(name = "positionId"), inverseJoinColumns = @JoinColumn(name = "skillId"))
    private List<SkillsEntity> skillsEntities = new ArrayList<>();

    @OneToMany(mappedBy = "positionEntity")
    private List<QuestionEntity> questionEntities;

//    @OneToMany(mappedBy = "position")
//    private List<JobPostingEntity> jobPostingEntities;

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public FieldEntity getField() {
        return field;
    }

    public void setField(FieldEntity field) {
        this.field = field;
    }

    public List<QuestionEntity> getQuestionEntities() {
        return questionEntities;
    }

    public void setQuestionEntities(List<QuestionEntity> questionEntities) {
        this.questionEntities = questionEntities;
    }

//    public List<JobPostingEntity> getJobPostingEntities() {
//        return jobPostingEntities;
//    }
//
//    public void setJobPostingEntities(List<JobPostingEntity> jobPostingEntities) {
//        this.jobPostingEntities = jobPostingEntities;
//    }

    public List<SkillsEntity> getSkillsEntities() {
        return skillsEntities;
    }

    public void setSkillsEntities(List<SkillsEntity> skillsEntities) {
        this.skillsEntities = skillsEntities;
    }
}
