package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.dto.ProvinceDTO;
import com.java08.quanlituyendung.service.IProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/province")
@RequiredArgsConstructor
public class ProvinceController {
    @Autowired
    private IProvinceService service;

    @GetMapping
    public List<ProvinceDTO> getAllProvince(){
        return service.getAllProvince();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProvinceById(@PathVariable("id") Long id){
        ProvinceDTO dto = service.getProvinceById(id);
        if (dto != null){
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ProvinceDTO addProvince(@RequestBody ProvinceDTO provinceDTO){
        return service.addProvince(provinceDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProvince(@PathVariable("id") Long id, @RequestBody ProvinceDTO provinceDTO){
        ProvinceDTO updateDTO = service.updateProvince(id, provinceDTO);
        if (updateDTO != null){
            return new ResponseEntity<>(updateDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProvince(@RequestBody Long[] idList){
        for (Long id : idList){
            if (service.deleteProvince(id) != (long) -1){
                return new ResponseEntity<>(id, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
