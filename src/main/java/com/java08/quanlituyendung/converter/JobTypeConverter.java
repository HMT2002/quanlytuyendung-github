package com.java08.quanlituyendung.converter;

import com.java08.quanlituyendung.dto.JobTypeDTO;
import com.java08.quanlituyendung.entity.JobTypeEntity;

public class JobTypeConverter {
    public static JobTypeDTO toDto(JobTypeEntity jobType){
        JobTypeDTO jobTypeDTO = new JobTypeDTO();
        jobTypeDTO.setId(jobType.getId());
        jobTypeDTO.setJobName(jobType.getJobName());

        return jobTypeDTO;
    }

    public static JobTypeEntity toEntity(JobTypeDTO jobTypeDTO){
        JobTypeEntity jobTypeEntity = new JobTypeEntity();
        jobTypeEntity.setId(jobTypeDTO.getId());
        jobTypeEntity.setJobName(jobTypeDTO.getJobName());

        return  jobTypeEntity;
    }
}
