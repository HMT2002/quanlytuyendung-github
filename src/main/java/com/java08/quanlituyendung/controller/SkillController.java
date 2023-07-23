package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.dto.*;
import com.java08.quanlituyendung.service.IPositionService;
import com.java08.quanlituyendung.service.ISkillService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/skill")
@RequiredArgsConstructor
public class SkillController {

    private final ISkillService skillService;
    @PostMapping
    public ResponseEntity<ResponseDTO> createSkill(@RequestBody SkillRequestDTO request) {
        return ResponseEntity.ok(skillService.createSkill(request));
    }

    @GetMapping

    public ResponseEntity<ResponseDTO> getSkill(@RequestBody SkillRequestDTO request) {
        return ResponseEntity.ok(skillService.getSkill(request));

    }

        @GetMapping("/{id}")

    public ResponseEntity<ResponseDTO> getSkillById(@PathVariable long id) {
        return ResponseEntity.ok(skillService.getSkillById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateSkill(@PathVariable long id,@RequestBody SkillRequestDTO request) {
        return ResponseEntity.ok(skillService.updateSkill(id,request));

    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteSkill(@RequestBody SkillRequestDTO request) {
        return ResponseEntity.ok(skillService.deleteSkill(request));
    }
}
