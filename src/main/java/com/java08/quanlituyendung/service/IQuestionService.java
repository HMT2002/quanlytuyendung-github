package com.java08.quanlituyendung.service;

import com.java08.quanlituyendung.dto.QuestionDTO;

import java.util.List;

public interface IQuestionService {
    QuestionDTO save(QuestionDTO questionDTO);
    List<QuestionDTO> getAllQuestion();
    QuestionDTO updateQuestion(QuestionDTO questionDTO);
    void delete(Long[] ids);
}
