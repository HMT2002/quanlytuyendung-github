package com.java08.quanlituyendung.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Interview")
public class InterviewEntity extends BaseEntity{
    @Column(name = "dateTime")
    private String dateTime;
    @Column(name = "score")
    private int score;
    @Column(name = "note")
    private String note;

    @ManyToMany
    @JoinTable(name = "Interview_UserAccount",
            joinColumns = @JoinColumn(name = "interviewId"),
            inverseJoinColumns = @JoinColumn(name = "userAccountId"))
    private List<UserAccountEntity> accountEntityList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "toolId")
    private InterviewToolEntity interviewToolEntity;

    @ManyToOne
    @JoinColumn(name = "userAccountId")
    private UserAccountEntity userAccountEntity;

    @ManyToOne
    @JoinColumn(name = "jobPostId")
    private JobPostingEntity jobPostingEntity;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<UserAccountEntity> getAccountEntityList() {
        return accountEntityList;
    }

    public void setAccountEntityList(List<UserAccountEntity> accountEntityList) {
        this.accountEntityList = accountEntityList;
    }

    public InterviewToolEntity getInterviewToolEntity() {
        return interviewToolEntity;
    }

    public void setInterviewToolEntity(InterviewToolEntity interviewToolEntity) {
        this.interviewToolEntity = interviewToolEntity;
    }

    public UserAccountEntity getUserAccountEntity() {
        return userAccountEntity;
    }

    public void setUserAccountEntity(UserAccountEntity userAccountEntity) {
        this.userAccountEntity = userAccountEntity;
    }

    public JobPostingEntity getJobPostingEntity() {
        return jobPostingEntity;
    }

    public void setJobPostingEntity(JobPostingEntity jobPostingEntity) {
        this.jobPostingEntity = jobPostingEntity;
    }
}
