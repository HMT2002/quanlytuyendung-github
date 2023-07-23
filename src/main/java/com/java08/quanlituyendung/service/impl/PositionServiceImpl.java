package com.java08.quanlituyendung.service.impl;

import com.java08.quanlituyendung.converter.PositionConverter;
import com.java08.quanlituyendung.dto.PositionRequestDTO;
import com.java08.quanlituyendung.dto.SkillRequestDTO;
import com.java08.quanlituyendung.dto.ResponseDTO;
import com.java08.quanlituyendung.repository.FieldRepository;
import com.java08.quanlituyendung.repository.JobPostingRepository;
import com.java08.quanlituyendung.repository.PositionRepository;
import com.java08.quanlituyendung.repository.QuestionRepository;
import com.java08.quanlituyendung.repository.SkillRepository;
import com.java08.quanlituyendung.utils.Constant;
import com.java08.quanlituyendung.service.IPositionService;
import com.java08.quanlituyendung.entity.JobPostingEntity;
import com.java08.quanlituyendung.entity.PositionEntity;
import com.java08.quanlituyendung.entity.QuestionEntity;
import com.java08.quanlituyendung.entity.SkillsEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements IPositionService {

        private final PositionConverter positionConverter;

        @Autowired
        private final PositionRepository positionRepository;

        @Autowired
        private final QuestionRepository questionRepository;

        @Autowired
        private final SkillRepository skillRepository;

        @Autowired
        private final JobPostingRepository jobPostingRepository;

        @Autowired
        private final FieldRepository fieldRepository;

        @Override
        public ResponseDTO createPosition(PositionRequestDTO request) {
                System.out.println("create Position");

                try {
                        var position = positionConverter.toEntity(request);

                        var savedPosition = positionRepository.save(position);
                        System.out.println("Save success");
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Success save position")
                                                        .response(savedPosition)
                                                        .build())
                                        .getBody();
                } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Fail save position")
                                                        .response(ex.getMessage())
                                                        .build())
                                        .getBody();
                }

                
        }

        @Override
        public ResponseDTO getPosition(PositionRequestDTO request) {
                var position = positionRepository.findAll();
                return ResponseEntity.status(HttpStatus.OK).body(
                                ResponseDTO.builder()
                                                .message("Got all position")
                                                .response(position)
                                                .build())
                                .getBody();
        }

        @Override
        public ResponseDTO getPositionById(long id) {
                var position = positionRepository.findById(id);

                return ResponseEntity.status(HttpStatus.OK).body(
                                ResponseDTO.builder()
                                                .message("Got position")
                                                .response(position)
                                                .build())
                                .getBody();
        }

        @Override
        public ResponseDTO updatePosition(long id, PositionRequestDTO request) {
                try {
                        var position = positionConverter.toEntityByIdAndUpdate(id,request);
                        positionRepository.save(position);
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Updated position")
                                                        .response(position)
                                                        .build())
                                        .getBody();
                } catch (Exception ex) {
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Failed update position")
                                                        .response("Error: "+ex.getMessage())
                                                        .build())
                                        .getBody();
                }
        }


        public ResponseDTO deleteListPosition(PositionRequestDTO request) {

                try {
                        positionRepository.deleteAllById(request.getDeleteIds());
                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Delete position")
                                                        .response("Position is deleted")
                                                        .build())
                                        .getBody();
                } catch (Exception ex) {

                        return ResponseEntity.status(HttpStatus.OK).body(
                                        ResponseDTO.builder()
                                                        .message("Failed delete position")
                                                        .response("Error: "+ex.getMessage())
                                                        .build())
                                        .getBody();
                }
        }

}
