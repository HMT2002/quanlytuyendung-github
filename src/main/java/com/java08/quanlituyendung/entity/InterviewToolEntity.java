package com.java08.quanlituyendung.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "InterviewTool")
public class InterviewToolEntity extends BaseEntity{

    @Column(name = "name")
    private String name;
    @Column(name = "website")
    private String website;

    public InterviewToolEntity() {
    }

    public InterviewToolEntity(String name, String website) {
        this.name = name;
        this.website = website;
    }

    @OneToMany(mappedBy = "interviewToolEntity")
    private List<InterviewEntity> interviewEntityList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<InterviewEntity> getInterviewEntityList() {
        return interviewEntityList;
    }

    public void setInterviewEntityList(List<InterviewEntity> interviewEntityList) {
        this.interviewEntityList = interviewEntityList;
    }
}
