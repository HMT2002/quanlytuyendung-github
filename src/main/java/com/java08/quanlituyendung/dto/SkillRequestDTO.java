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
public class SkillRequestDTO {

    long ID;
    String skillName;
    Long positionId;


    List<Long>  questionEntities;

    List<Long> jobPostingEntities;

    List<Long> deleteIds;

}
