package com.java08.quanlituyendung.service.impl;

import com.java08.quanlituyendung.converter.ProvinceConverter;
import com.java08.quanlituyendung.dto.ProvinceDTO;
import com.java08.quanlituyendung.entity.ProvinceEntity;
import com.java08.quanlituyendung.repository.ProvinceRepository;
import com.java08.quanlituyendung.service.IProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements IProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public ProvinceDTO addProvince(ProvinceDTO province) {
        return ProvinceConverter.toDto(provinceRepository.save(ProvinceConverter.toEntity(province)));
    }

    @Override
    public ProvinceDTO updateProvince(Long id, ProvinceDTO province) {
        Optional<ProvinceEntity> entity = provinceRepository.findById(id);
        if (entity.isEmpty()){
            return null;
        }

        ProvinceEntity provinceEntity = entity.get();
        provinceEntity.setProvinceName(province.getProvinceName());
        provinceRepository.save(provinceEntity);
        return province;
    }

    @Override
    public Long deleteProvince(Long id) {
        if (provinceRepository.existsById(id)){
            provinceRepository.deleteById(id);
            return (long) -1;
        }
        return id;
    }

    @Override
    public ProvinceDTO getProvinceById(Long id) {
        Optional<ProvinceEntity> entity = provinceRepository.findById(id);
        return entity.map(ProvinceConverter::toDto).orElse(null);
    }

    @Override
    public List<ProvinceDTO> getAllProvince() {
        List<ProvinceDTO> list = new ArrayList<>();
        for (ProvinceEntity province : provinceRepository.findAll()){
            list.add(ProvinceConverter.toDto(province));
        }
        return list;
    }
}
