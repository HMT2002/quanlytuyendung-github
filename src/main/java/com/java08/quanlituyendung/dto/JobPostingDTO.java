package com.java08.quanlituyendung.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor

public class JobPostingDTO {
    private Long id;
    private String name;
    private String position;
    private String language;
    private String location;
    private String salary;
    private String number;
    private String workingForm;
    private String sex;
    private String experience;
    private String detailLocation;
    private String detailJob;
    private String requirements;
    private String interest;
    private String image;
    private Boolean status;
}
