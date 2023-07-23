package com.java08.quanlituyendung.service.impl;

import com.java08.quanlituyendung.converter.FieldConverter;
import com.java08.quanlituyendung.dto.FieldDTO;
import com.java08.quanlituyendung.entity.FieldEntity;
import com.java08.quanlituyendung.repository.FieldRepository;
import com.java08.quanlituyendung.service.IFieldService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FieldServiceImpl implements IFieldService {
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private FieldConverter fieldConverter;
    @Override
    public List<FieldDTO> getAllField(){
        List<FieldDTO> listFieldDTO= new ArrayList<>();
        List<FieldEntity> listFieldEntity = fieldRepository.findAll();
        for(FieldEntity fieldEntity : listFieldEntity){
            FieldDTO fieldDTO = fieldConverter.toDTO(fieldEntity);
            listFieldDTO.add(fieldDTO);
        }
        return listFieldDTO;
    }
    public FieldDTO save(FieldDTO fieldDTO) {
        FieldEntity fieldEntity = fieldConverter.toEntity(fieldDTO);
        fieldEntity = fieldRepository.save(fieldEntity);
        fieldDTO = fieldConverter.toDTO(fieldEntity);
        return fieldDTO;
    }

    public FieldDTO update ( FieldDTO fieldDTO) {
        FieldEntity  fieldEntity = fieldRepository.findOneById(fieldDTO.getId());
        fieldEntity.setFieldName(fieldDTO.getFieldName());
        fieldEntity = fieldRepository.save(fieldEntity);
        return fieldConverter.toDTO(fieldEntity);
    }


    public boolean delete(Long[] ids){
        fieldRepository.deleteAllById(Arrays.asList(ids));
        return true;
    }

}
