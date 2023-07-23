package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.dto.InterviewDTO;
import com.java08.quanlituyendung.service.IInterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.java08.quanlituyendung.dto.InterviewDTO;
import com.java08.quanlituyendung.service.IInterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {
    private final IInterviewService service;

    @GetMapping
    public List<InterviewDTO> getAllInterviews() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInterview(@PathVariable("id") Long id) {
        InterviewDTO dto = service.getInterviewById(id);
        if (dto != null)
            return new ResponseEntity<>(dto, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInterview(@PathVariable("id") Long id, @RequestParam(required = false, defaultValue = "false") Boolean overwrite, @RequestBody InterviewDTO dto) {
        InterviewDTO updated = service.updateInterview(dto, id, overwrite);
        if (updated != null)
            return new ResponseEntity<>(updated, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping
    public void deleteInterview(@RequestBody List<Long> ids) {
        for (Long id: ids) {
            service.removeInterview(id);
        }
    }

    @PostMapping("/create")
    public InterviewDTO addInterview(@RequestBody InterviewDTO interview) {
        return service.addInterview(interview);
    }

}
