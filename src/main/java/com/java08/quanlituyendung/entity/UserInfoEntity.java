package com.java08.quanlituyendung.entity;

import org.json.simple.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UserInfo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoEntity extends BaseEntity {

    @Column(name = "fullName")
    private String fullName;
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "dob")
    private String dob;
    @Column(name = "address")
    private String address;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "phone")
    private String phone;

    @Column(name = "cv",columnDefinition = "text")
    private String cv;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "accountId")
    @JsonIgnore
    private UserAccountEntity userAccountInfo;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public UserAccountEntity getUserAccountInfo() {
        return userAccountInfo;
    }

    public void setUserAccountInfo(UserAccountEntity userAccountInfo) {
        this.userAccountInfo = userAccountInfo;
    }
}
