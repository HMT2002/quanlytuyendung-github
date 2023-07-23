package com.java08.quanlituyendung.service;

import com.java08.quanlituyendung.dto.FieldDTO;

import java.util.List;

public interface IFieldService {
    List<FieldDTO> getAllField();
    FieldDTO save(FieldDTO fieldDTO);
    FieldDTO update(FieldDTO fieldDTO);
    boolean delete(Long[] ids);
}
