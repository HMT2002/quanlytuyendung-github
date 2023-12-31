package com.java08.quanlituyendung.converter;

import com.java08.quanlituyendung.dto.JobPostingDTO;
import com.java08.quanlituyendung.entity.JobPostingEntity;
import org.springframework.stereotype.Component;

@Component
public class JobPostingConverter {
    public JobPostingEntity toEntity(JobPostingDTO dto) {
        return JobPostingEntity.builder()
                .name(dto.getName())
                .position(dto.getPosition())
                .language(dto.getLanguage())
                .location(dto.getLocation())
                .salary(dto.getSalary())
                .number(dto.getNumber())
                .image(dto.getImage())
                .workingForm(dto.getWorkingForm())
                .sex(dto.getSex())
                .experience(dto.getExperience())
                .detailLocation(dto.getDetailLocation())
                .detailJob(dto.getDetailJob())
                .requirements(dto.getRequirements())
                .interest(dto.getInterest())
                .status(true)
                .build();
    }

    public JobPostingDTO toDTO(JobPostingEntity entity) {
        return JobPostingDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .position(entity.getPosition())
                .language(entity.getLanguage())
                .location(entity.getLocation())
                .salary(entity.getSalary())
                .number(entity.getNumber())
                .image(entity.getImage())
                .workingForm(entity.getWorkingForm())
                .sex(entity.getSex())
                .experience(entity.getExperience())
                .detailLocation(entity.getDetailLocation())
                .detailJob(entity.getDetailJob())
                .requirements(entity.getRequirements())
                .interest(entity.getInterest())
                .status(entity.getStatus())
                .build();
    }

   public JobPostingEntity toEntity(JobPostingDTO dto, JobPostingEntity entity) {
       if (dto.getName() != null) {
           entity.setName(dto.getName());
       }
       if (dto.getPosition() != null) {
           entity.setPosition(dto.getPosition());
       }
       if (dto.getLanguage() != null) {
           entity.setLanguage(dto.getLanguage());
       }
       if (dto.getLocation() != null) {
           entity.setLocation(dto.getLocation());
       }
       if (dto.getSalary() != null) {
           entity.setSalary(dto.getSalary());
       }
       if (dto.getNumber() != null) {
           entity.setNumber(dto.getNumber());
       }
       if (dto.getImage() != null) {
           entity.setImage(dto.getImage());
       }
       if (dto.getWorkingForm() != null) {
           entity.setWorkingForm(dto.getWorkingForm());
       }
       if (dto.getSex() != null) {
           entity.setSex(dto.getSex());
       }
       if (dto.getExperience() != null) {
           entity.setExperience(dto.getExperience());
       }
       if (dto.getDetailLocation() != null) {
           entity.setDetailLocation(dto.getDetailLocation());
       }
       if (dto.getDetailJob() != null) {
           entity.setDetailJob(dto.getDetailJob());
       }
       if (dto.getRequirements() != null) {
           entity.setRequirements(dto.getRequirements());
       }
       if (dto.getInterest() != null) {
           entity.setInterest(dto.getInterest());
       }
       if(dto.getStatus() != null){
           entity.setStatus(dto.getStatus());
       }
       return entity;
   }
}
