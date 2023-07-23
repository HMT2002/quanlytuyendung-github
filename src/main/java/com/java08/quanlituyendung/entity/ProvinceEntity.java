package com.java08.quanlituyendung.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Province")
public class ProvinceEntity extends BaseEntity {
    @Column(name = "provinceName")
    private String provinceName;

    @OneToMany(mappedBy = "provinceEntity")
    private List<JobPostingEntity> jobPostingEntities = new ArrayList<>();

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public List<JobPostingEntity> getJobPostingEntities() {
        return jobPostingEntities;
    }

    public void setJobPostingEntities(List<JobPostingEntity> jobPostingEntities) {
        this.jobPostingEntities = jobPostingEntities;
    }
}
