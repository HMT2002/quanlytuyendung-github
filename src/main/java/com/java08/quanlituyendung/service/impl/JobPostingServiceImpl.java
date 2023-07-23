package com.java08.quanlituyendung.service.impl;

import com.java08.quanlituyendung.converter.JobPostingConverter;
import com.java08.quanlituyendung.dto.JobPostingDTO;
import com.java08.quanlituyendung.dto.ResponseObject;
import com.java08.quanlituyendung.entity.JobPostingEntity;
import com.java08.quanlituyendung.repository.JobPostingRepository;
import com.java08.quanlituyendung.service.IJobPostingService;
import com.java08.quanlituyendung.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobPostingServiceImpl implements IJobPostingService{
    @Autowired
    JobPostingConverter jobPostingConverter;

    @Autowired
    JobPostingRepository jobPostingRepository;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public ResponseEntity<ResponseObject> getAllJobPosting() {
        List<JobPostingDTO> listJobPostingDTO = new ArrayList<>();
        List<JobPostingEntity> listJobPostingEntity = jobPostingRepository.findAll();
        for(JobPostingEntity jobPostingEntity : listJobPostingEntity){
            JobPostingDTO jobPostingDTO = jobPostingConverter.toDTO(jobPostingEntity);
            listJobPostingDTO.add(jobPostingDTO);
        }

//        if (listJobPostingEntity.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                    ResponseObject.builder()
//                            .status("ERROR")
//                            .message(Constant.NO_DATA_FOUND)
//                            .data(listJobPostingDTO)
//                            .build());
//        }
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .status("OK")
                        .message(Constant.SUCCESS)
                        .data(listJobPostingDTO)
                        .build());
    }
    public ResponseEntity<ResponseObject> save(JobPostingDTO jobPostingDTO) {
        JobPostingEntity jobPostingEntity;
       if (jobPostingDTO.getId() != null) {
           JobPostingEntity oldJobPostingEntity = jobPostingRepository.findOneById(jobPostingDTO.getId());
           jobPostingEntity = jobPostingConverter.toEntity(jobPostingDTO, oldJobPostingEntity);
           jobPostingEntity.setUpdateDate(sdf.format(Date.valueOf(LocalDate.now())));
       } else {
            jobPostingEntity = jobPostingConverter.toEntity(jobPostingDTO);
            jobPostingEntity.setCreateDate(sdf.format(Date.valueOf(LocalDate.now())));
       }
        jobPostingEntity = jobPostingRepository.save(jobPostingEntity);
        jobPostingDTO = jobPostingConverter.toDTO(jobPostingEntity);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .status("OK")
                        .message(Constant.SUCCESS)
                        .data(jobPostingDTO)
                        .build());
    }

    public ResponseEntity<ResponseObject> delete(Long id) {
            JobPostingEntity jobPostingEntity = jobPostingRepository.findOneById(id);
            jobPostingEntity.setStatus(false);
            jobPostingRepository.save(jobPostingEntity);

        return ResponseEntity.status(HttpStatus.OK).body(
                ResponseObject.builder()
                        .status("OK")
                        .message(Constant.Delete_SUCCESS)
                        .build());
    }
}
