package com.java08.quanlituyendung.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Blacklist")
public class BlacklistEntity extends BaseEntity {
    @Column(name = "startTime")
    private String startTime;
    @Column(name = "endTime")
    private String endTime;
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private UserAccountEntity userAccountEntity;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserAccountEntity getUserAccountEntity() {
        return userAccountEntity;
    }

    public void setUserAccountEntity(UserAccountEntity userAccountEntity) {
        this.userAccountEntity = userAccountEntity;
    }
}
