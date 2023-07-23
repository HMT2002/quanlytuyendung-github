package com.java08.quanlituyendung.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewDTO extends AbstractDTO {
    private Long candidateId;
    private Long jobPostId;
    private Long toolId;
    private String datetime;
    private Integer score;
    private String note;
    private List<Long> interviewerIds;
}
