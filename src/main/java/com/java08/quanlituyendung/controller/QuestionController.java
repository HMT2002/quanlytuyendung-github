package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.dto.QuestionDTO;
import com.java08.quanlituyendung.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/question")
public class QuestionController {
    @Autowired
    private IQuestionService iQuestionService;
    @PostMapping
    public QuestionDTO createQuestion(@RequestBody QuestionDTO model) {
        return iQuestionService.save(model);
    }

    @GetMapping
    public List<QuestionDTO> getAllQuestion() {
        return iQuestionService.getAllQuestion();
    }

    @DeleteMapping
    public ResponseEntity<String> delete(@RequestBody Long[] ids) {
        iQuestionService.delete(ids);
        return ResponseEntity.ok("Delete Success!");
    }

    @PutMapping(value = "/{id}")
    public QuestionDTO updateQuestion(@RequestBody QuestionDTO model, @PathVariable("id") long id) {
        model.setId(id);
        return iQuestionService.updateQuestion(model);
    }
}
