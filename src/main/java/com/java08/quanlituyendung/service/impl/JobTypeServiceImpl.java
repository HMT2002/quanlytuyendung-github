package com.java08.quanlituyendung.service.impl;

import com.java08.quanlituyendung.converter.JobTypeConverter;
import com.java08.quanlituyendung.dto.JobTypeDTO;
import com.java08.quanlituyendung.entity.JobTypeEntity;
import com.java08.quanlituyendung.repository.JobTypeRepository;
import com.java08.quanlituyendung.service.IJobTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobTypeServiceImpl implements IJobTypeService {
    @Autowired
    private JobTypeRepository jobTypeRepository;

    @Override
    public JobTypeDTO addJobType(JobTypeDTO jobType) {
        return JobTypeConverter.toDto(jobTypeRepository.save(JobTypeConverter.toEntity(jobType)));
    }

    @Override
    public JobTypeDTO updateJobType(Long id, JobTypeDTO jobType) {
        Optional<JobTypeEntity> entity = jobTypeRepository.findById(id);
        if (entity.isEmpty()){
            return null;
        }

        JobTypeEntity jobTypeEntity = entity.get();

        jobTypeEntity.setJobName(jobType.getJobName());
        jobTypeRepository.save(jobTypeEntity);
        return jobType;
    }

    @Override
    public void delete(Long[] ids) {
        jobTypeRepository.deleteAllById(Arrays.asList(ids));
    }


    @Override
    public List<JobTypeDTO> getAllJobType() {
        List<JobTypeDTO> list = new ArrayList<>();
        for (JobTypeEntity jobType : jobTypeRepository.findAll()){
            list.add(JobTypeConverter.toDto(jobType));
        }
        return list;
    }

    @Override
    public JobTypeDTO getJobTypeById(Long id) {
        Optional<JobTypeEntity> entity = jobTypeRepository.findById(id);
        return entity.map(JobTypeConverter::toDto).orElse(null);
    }
}
