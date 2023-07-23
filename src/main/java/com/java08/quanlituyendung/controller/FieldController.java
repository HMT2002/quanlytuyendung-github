package com.java08.quanlituyendung.controller;

import com.java08.quanlituyendung.dto.FieldDTO;
import com.java08.quanlituyendung.service.IFieldService;
import com.java08.quanlituyendung.service.impl.FieldServiceImpl;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/field")
public class FieldController {

    @Autowired
    private IFieldService fieldService;

    @GetMapping
    public ResponseEntity<List<FieldDTO>> getAllField() {
        return new ResponseEntity<>(fieldService.getAllField(), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<FieldDTO> create(@Valid @RequestBody FieldDTO model) {
        return new ResponseEntity<>(fieldService.save(model), HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<FieldDTO> update( @PathVariable long id,@RequestBody FieldDTO model) {
        model.setId(id);
        return new ResponseEntity<>(fieldService.update(model), HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Long[] ids) {

        return new ResponseEntity<>(fieldService.delete(ids) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // Nếu validate fail thì trả về 400
    public String handleBindException(BindException e) {
        // Trả về message của lỗi đầu tiên
        String errorMessage = "Request không hợp lệ";
        if (e.getBindingResult().hasErrors())
            e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return errorMessage;
    }
}
