package com.java08.quanlituyendung.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "JobType")
public class JobTypeEntity extends BaseEntity{

    @Column(name = "jobName")
    private String jobName;

    @OneToMany(mappedBy = "jobTypeEntity")
    private List<JobPostingEntity> jobPostingEntities = new ArrayList<>();

    @OneToMany(mappedBy = "jobType")
    private List<QuestionEntity> questionEntities = new ArrayList<>();

    public JobTypeEntity() {
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public List<JobPostingEntity> getJobPostingEntities() {
        return jobPostingEntities;
    }

    public void setJobPostingEntities(List<JobPostingEntity> jobPostingEntities) {
        this.jobPostingEntities = jobPostingEntities;
    }

    public List<QuestionEntity> getQuestionEntities() {
        return questionEntities;
    }

    public void setQuestionEntities(List<QuestionEntity> questionEntities) {
        this.questionEntities = questionEntities;
    }
}