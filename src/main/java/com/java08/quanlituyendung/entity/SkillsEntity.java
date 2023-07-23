package com.java08.quanlituyendung.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Skills")
public class SkillsEntity extends BaseEntity {
    @Column(name = "skillName")
    private String skillName;

    @ManyToMany(mappedBy = "skillsEntities")
    private List<PositionEntity> positions = new ArrayList<>();

    @ManyToMany(mappedBy = "skillsEntities")
    private List<JobPostingEntity> jobPostings = new ArrayList<>();

    @ManyToMany(mappedBy = "skillsEntities")
    private List<QuestionEntity> questions = new ArrayList<>();

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public List<PositionEntity> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionEntity> positions) {
        this.positions = positions;
    }

    public List<JobPostingEntity> getJobPostings() {
        return jobPostings;
    }

    public void setJobPostings(List<JobPostingEntity> jobPostings) {
        this.jobPostings = jobPostings;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }
}
