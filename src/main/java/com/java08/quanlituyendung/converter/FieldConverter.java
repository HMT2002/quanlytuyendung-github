package com.java08.quanlituyendung.converter;

import com.java08.quanlituyendung.dto.FieldDTO;
import com.java08.quanlituyendung.entity.FieldEntity;
import org.springframework.stereotype.Component;

@Component
public class FieldConverter {
    public FieldEntity toEntity(FieldDTO dto){
        FieldEntity entity = new FieldEntity();
        entity.setFieldName(dto.getFieldName());
        return entity;
    }
    public FieldDTO toDTO(FieldEntity entity){
        return FieldDTO.builder()
                .fieldName(entity.getFieldName())
                .id(entity.getId())
                .build();
    }
    public FieldEntity toEntity(FieldDTO dto, FieldEntity entity){
        entity.setFieldName(dto.getFieldName());
        return entity;
    }
}
