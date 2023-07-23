package com.java08.quanlituyendung.service.impl;

import com.java08.quanlituyendung.converter.QuestionConverter;
import com.java08.quanlituyendung.dto.QuestionDTO;
import com.java08.quanlituyendung.entity.QuestionEntity;
//import com.java08.quanlituyendung.repository.JobTypeRepository;
import com.java08.quanlituyendung.repository.QuestionRepository;
import com.java08.quanlituyendung.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class QuestionServiceIml implements IQuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionConverter questionConverter;

    @Override
    public QuestionDTO save(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = questionConverter.toEntity(questionDTO);
        questionEntity = questionRepository.save(questionEntity);
        questionDTO = questionConverter.toDTO(questionEntity);
        return questionDTO;
    }

    @Override
    public List<QuestionDTO> getAllQuestion(){
        List<QuestionDTO> questionDTOList= new ArrayList<>();
        List<QuestionEntity> questionEntityList = questionRepository.findAll();
        for(QuestionEntity questionEntity : questionEntityList){
            QuestionDTO questionDTO = questionConverter.toDTO(questionEntity);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }

//    public QuestionDTO updateQuestion(QuestionDTO questionDTO) {
//        QuestionEntity  questionEntity = questionRepository.findById(questionDTO.getId())
//                .orElseThrow(RuntimeException::new);
//        questionEntity.setQuestion(questionDTO.getQuestion());
//        questionEntity = questionRepository.save(questionEntity);
//        return questionConverter.toDTO(questionEntity);
//    }

    public QuestionDTO updateQuestion(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = questionRepository.findById(questionDTO.getId())
                .orElseThrow(RuntimeException::new);
        if (questionDTO.getQuestion() != null) {
            questionEntity.setQuestion(questionDTO.getQuestion());
        }
        questionEntity = questionRepository.save(questionEntity);
        return questionConverter.toDTO(questionEntity);
    }

    @Override
    public void delete(Long[] ids) {

        questionRepository.deleteAllById(Arrays.asList(ids));
    }
}
