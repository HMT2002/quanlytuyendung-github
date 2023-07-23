package com.java08.quanlituyendung.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.java08.quanlituyendung.entity.InterviewEntity;

public interface InterviewRepository extends JpaRepository<InterviewEntity, Long> {
    
}

