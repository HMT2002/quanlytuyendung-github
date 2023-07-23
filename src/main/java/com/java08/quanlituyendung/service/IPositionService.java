package com.java08.quanlituyendung.service;

import com.java08.quanlituyendung.dto.PositionRequestDTO;
import com.java08.quanlituyendung.dto.SkillRequestDTO;
import com.java08.quanlituyendung.dto.ResponseDTO;


public interface IPositionService {
        
     ResponseDTO createPosition(PositionRequestDTO CRUDRequest);
     ResponseDTO getPosition(PositionRequestDTO CRUDRequest);
          ResponseDTO getPositionById(long id);

     ResponseDTO updatePosition(long id,PositionRequestDTO CRUDRequest);
     ResponseDTO deleteListPosition(PositionRequestDTO request) ;
    
}
