package com.java08.quanlituyendung.converter;

import com.java08.quanlituyendung.dto.QuestionDTO;
import com.java08.quanlituyendung.entity.QuestionEntity;
import org.springframework.stereotype.Component;

@Component
public class QuestionConverter {
    public QuestionEntity toEntity(QuestionDTO questionDTO) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setQuestion(questionDTO.getQuestion());
        return questionEntity;
    }

    public QuestionDTO toDTO(QuestionEntity questionEntity) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQuestion(questionEntity.getQuestion());
        questionDTO.setId(questionEntity.getId());
        return questionDTO;
    }

    public QuestionEntity toEntity(QuestionDTO questionDTO, QuestionEntity questionEntity){
        questionEntity.setQuestion(questionDTO.getQuestion());
        return questionEntity;
    }
}
