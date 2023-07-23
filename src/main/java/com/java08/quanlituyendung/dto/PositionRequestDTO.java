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
public class PositionRequestDTO {


    long  ID;
    String positionName;

    long fieldID;


    List<Long>  questionEntities;

    List<Long>  skillEntities;

    List<Long> jobPostingEntities;

    List<Long> deleteIds;

}
