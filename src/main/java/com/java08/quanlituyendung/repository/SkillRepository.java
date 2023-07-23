
package com.java08.quanlituyendung.repository;

import com.java08.quanlituyendung.entity.SkillsEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<SkillsEntity, Long> {

    SkillsEntity findById(long id);

    

}
