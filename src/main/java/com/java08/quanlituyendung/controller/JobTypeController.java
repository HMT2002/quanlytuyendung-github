package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.dto.JobTypeDTO;
import com.java08.quanlituyendung.service.IJobTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job-type")
@RequiredArgsConstructor
public class JobTypeController {
    @Autowired
    private IJobTypeService service;

    @GetMapping
    public List<JobTypeDTO> getAllJobType() {
        return service.getAllJobType();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobTypeById(@PathVariable("id") Long id) {
        JobTypeDTO dto = service.getJobTypeById(id);
        if (dto != null) {
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public JobTypeDTO addJobType(@RequestBody JobTypeDTO jobTypeDTO) {
        return service.addJobType(jobTypeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJobType(@PathVariable("id") Long id, @RequestBody JobTypeDTO dto) {
        JobTypeDTO updateDTO = service.updateJobType(id, dto);
        if (updateDTO != null) {
            return new ResponseEntity<>(updateDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //    @DeleteMapping("/delete")
//    public ResponseEntity<?> deleteJobType(@RequestBody List<Long> idList){
//        for (Long id : idList){
//            if (service.deleteJobType(id) != (long) -1){
//                return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
//            }
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    @DeleteMapping
    public void deleteJobType(@RequestBody Long[] ids) {
        service.delete(ids);
    }
}


