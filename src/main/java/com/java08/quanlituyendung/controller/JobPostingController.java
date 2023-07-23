package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.dto.JobPostingDTO;
import com.java08.quanlituyendung.dto.ResponseObject;
import com.java08.quanlituyendung.service.IJobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/job-posting")
public class JobPostingController {
    @Autowired
    private IJobPostingService jobPostingService;


//    @GetMapping
//    public List<JobPostingDTO> getAllJobPosting(){
//        return jobPostingService.getAllJobPosting();
//    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllJobPosting(){
        return jobPostingService.getAllJobPosting();
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createJobPosting(@RequestBody JobPostingDTO request){
        return jobPostingService.save(request);
    }
   @PutMapping(value = "/{id}")
   public ResponseEntity<ResponseObject> updateJobPosting(@RequestBody JobPostingDTO request,@PathVariable long id){
       request.setId(id);
       return jobPostingService.save(request);
   }
      @DeleteMapping
      public ResponseEntity<ResponseObject> delete(@RequestBody Long id) {
        return jobPostingService.delete(id);
    }
}
