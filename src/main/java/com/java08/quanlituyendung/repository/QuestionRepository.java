package com.java08.quanlituyendung.repository;

import com.java08.quanlituyendung.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    QuestionEntity findOneById(long id);
}
