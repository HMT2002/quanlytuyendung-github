package com.java08.quanlituyendung.service.impl;

import com.java08.quanlituyendung.dto.SkillRequestDTO;
import com.java08.quanlituyendung.entity.JobPostingEntity;
import com.java08.quanlituyendung.entity.QuestionEntity;
import com.java08.quanlituyendung.entity.SkillsEntity;
import com.java08.quanlituyendung.repository.PositionRepository;
import com.java08.quanlituyendung.repository.SkillRepository;
import com.java08.quanlituyendung.converter.PositionConverter;
import com.java08.quanlituyendung.converter.SkillConverter;
import com.java08.quanlituyendung.dto.ResponseDTO;
import com.java08.quanlituyendung.utils.Constant;
import com.java08.quanlituyendung.service.ISkillService;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements ISkillService {

        @Autowired
        private final SkillRepository skillRepository;

        private final PositionConverter positionConverter;

        private final SkillConverter skillConverter;


        @Override
        public ResponseDTO createSkill(SkillRequestDTO skillRequest) {
                try {

                        SkillsEntity entity = new SkillsEntity();
                        entity.setSkillName(skillRequest.getSkillName());

                        var savedSkill = skillRepository.save(entity);
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Create new skill")
                                                        .response(savedSkill)
                                                        .build())
                                        .getBody();
                } catch (Exception ex) {
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Failed create new skill")
                                                        .response(null)
                                                        .build())
                                        .getBody();
                }

        }

        @Override
        public ResponseDTO getSkill(SkillRequestDTO skillRequest) {
                try {
                        var allEntity = skillRepository.findAll();
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Get all skill")
                                                        .response(allEntity)
                                                        .build())
                                        .getBody();
                } catch (Exception ex) {
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Failed get all skill")
                                                        .response(null)
                                                        .build())
                                        .getBody();
                }
        }

        @Override
        public ResponseDTO getSkillById(long id) {
                try {
                        SkillsEntity entity = skillRepository.findById(id);
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Get skill")
                                                        .response(entity)
                                                        .build())
                                        .getBody();
                } catch (Exception ex) {
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Failed get skill")
                                                        .response(null)
                                                        .build())
                                        .getBody();
                }

        }

        @Override
        public ResponseDTO updateSkill(long id,SkillRequestDTO skillRequest) {

                try {
                        SkillsEntity entity = skillConverter.toEntityByIdAndUpdate(id, skillRequest);
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Update skill")
                                                        .response(skillRequest)
                                                        .build())
                                        .getBody();
                } catch (Exception ex) {
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Failed update skill")
                                                        .response(null)
                                                        .build())
                                        .getBody();
                }

        }


        public ResponseDTO deleteSkill(SkillRequestDTO request) {
                try {
                        skillRepository.deleteAllById(request.getDeleteIds());
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Delete skill")
                                                        .response("Skill has been deleted!")
                                                        .build())
                                        .getBody();
                } catch (Exception ex) {
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Failed delete skill")
                                                        .response(null)
                                                        .build())
                                        .getBody();
                }

        }

}
