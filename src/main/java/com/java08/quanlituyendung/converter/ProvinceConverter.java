package com.java08.quanlituyendung.converter;

import com.java08.quanlituyendung.dto.ProvinceDTO;
import com.java08.quanlituyendung.entity.ProvinceEntity;

public class ProvinceConverter {
    public static ProvinceDTO toDto(ProvinceEntity province){
        ProvinceDTO provinceDTO = new ProvinceDTO();
        provinceDTO.setId(province.getId());
        provinceDTO.setProvinceName(province.getProvinceName());

        return  provinceDTO;
    }

    public static ProvinceEntity toEntity(ProvinceDTO province){
        ProvinceEntity provinceEntity = new ProvinceEntity();
        provinceEntity.setId(province.getId());
        provinceEntity.setProvinceName(province.getProvinceName());

        return provinceEntity;
    }
}
