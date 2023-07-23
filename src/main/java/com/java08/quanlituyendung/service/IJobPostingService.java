package com.java08.quanlituyendung.service;

import com.java08.quanlituyendung.dto.JobPostingDTO;
import com.java08.quanlituyendung.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IJobPostingService {
    ResponseEntity<ResponseObject> getAllJobPosting();
    ResponseEntity<ResponseObject> save(JobPostingDTO jobPostingDTO);
    ResponseEntity<ResponseObject> delete(Long id);
}
