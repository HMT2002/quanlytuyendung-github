package com.java08.quanlituyendung.service;

import com.java08.quanlituyendung.dto.JobTypeDTO;

import java.util.List;

public interface IJobTypeService {
    JobTypeDTO addJobType(JobTypeDTO jobType);
    JobTypeDTO updateJobType(Long id, JobTypeDTO jobType);
    void delete(Long[] ids);
    List<JobTypeDTO> getAllJobType();
    JobTypeDTO getJobTypeById(Long id);
}
