package com.java08.quanlituyendung.entity;



import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cv")
public class CVEntity extends BaseEntity {
    @Column(name = "url")
    private String url;

    @Enumerated(EnumType.STRING)
    private State state;

    public enum State {
        INPROGRESS,
        APPROVED
    }

    @ManyToMany
    @JoinTable(name = "CV_JobPosting", joinColumns = @JoinColumn(name = "cvId"), inverseJoinColumns = @JoinColumn(name = "jobPostingId"))
    private List<JobPostingEntity> jobPostingEntities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userAccountId")
    private UserAccountEntity userAccountEntity;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<JobPostingEntity> getJobPostingEntities() {
        return jobPostingEntities;
    }

    public void setJobPostingEntities(List<JobPostingEntity> jobPostingEntities) {
        this.jobPostingEntities = jobPostingEntities;
    }

    public UserAccountEntity getUserAccountEntity() {
        return userAccountEntity;
    }

    public void setUserAccountEntity(UserAccountEntity userAccountEntity) {
        this.userAccountEntity = userAccountEntity;
    }
}
