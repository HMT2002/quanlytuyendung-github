
package com.java08.quanlituyendung.repository;

import com.java08.quanlituyendung.entity.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PositionRepository extends JpaRepository<PositionEntity, Long> {

    PositionEntity findById(long id);


}
