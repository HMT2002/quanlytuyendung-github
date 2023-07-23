package com.java08.quanlituyendung.service;

import com.java08.quanlituyendung.dto.ProvinceDTO;

import java.util.List;

public interface IProvinceService {
    ProvinceDTO addProvince(ProvinceDTO province);
    ProvinceDTO updateProvince(Long id, ProvinceDTO province);
    Long deleteProvince(Long id);
    ProvinceDTO getProvinceById(Long id);
    List<ProvinceDTO> getAllProvince();
}
