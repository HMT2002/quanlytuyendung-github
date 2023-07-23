package com.java08.quanlituyendung.repository;

import com.java08.quanlituyendung.entity.FieldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<FieldEntity, Long>{

    void deleteAllById(long[] ids);

    List<FieldEntity> findAllByFieldName(String fieldName);

    FieldEntity findOneById(long id);
}
