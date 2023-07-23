package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.dto.InterviewToolDTO;
import com.java08.quanlituyendung.service.IInterviewToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interview-tool")
@RequiredArgsConstructor
public class InterviewToolController {
    @Autowired private IInterviewToolService service;

    @GetMapping
    public List<InterviewToolDTO> getAllTools() {
        return service.getAll();
    }

    @PostMapping
    public InterviewToolDTO addTool(@RequestBody InterviewToolDTO tool) {
        return service.addTool(tool);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTool(@PathVariable("id") Long id) {
        InterviewToolDTO dto = service.getToolById(id);
        if (dto != null)
            return new ResponseEntity<>(dto, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<?> updateTool(@PathVariable("id") Long id, @RequestBody InterviewToolDTO dto) {
        InterviewToolDTO updated = service.updateToolById(dto, id);
        if (updated != null)
            return new ResponseEntity<>(updated, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping
    public void deleteTools(@RequestBody List<Long> ids) {
        for (Long id: ids) {
            service.removeToolById(id);
        }
    }
}

