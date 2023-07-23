package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.dto.*;
import com.java08.quanlituyendung.service.IPositionService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/position")
@RequiredArgsConstructor
public class PositionController {

    private final IPositionService positionRequest;

    @PostMapping
    public ResponseEntity<ResponseDTO> createPosition(@RequestBody PositionRequestDTO request) {
        System.out.println(request);
        return ResponseEntity.ok(positionRequest.createPosition(request));
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getPosition(@RequestBody PositionRequestDTO request) {
        return ResponseEntity.ok(positionRequest.getPosition(request));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getPositionById(@PathVariable long id) {
        return ResponseEntity.ok(positionRequest.getPositionById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updatePosition(@PathVariable long id,@RequestBody PositionRequestDTO request) {
        return ResponseEntity.ok(positionRequest.updatePosition(id,request));

    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO> deletePosition(@RequestBody PositionRequestDTO request) {
        return ResponseEntity.ok(positionRequest.deleteListPosition(request));

    }
}
