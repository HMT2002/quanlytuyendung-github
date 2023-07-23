package com.java08.quanlituyendung.service;

import com.java08.quanlituyendung.dto.SkillRequestDTO;
import com.java08.quanlituyendung.dto.ResponseDTO;


public interface ISkillService {
        
     ResponseDTO createSkill(SkillRequestDTO request);
     ResponseDTO getSkill(SkillRequestDTO request);
          ResponseDTO getSkillById(long id);

     ResponseDTO updateSkill(long id,SkillRequestDTO request);
     ResponseDTO deleteSkill(SkillRequestDTO request);
}
