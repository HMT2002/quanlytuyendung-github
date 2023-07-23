package com.java08.quanlituyendung.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterviewToolDTO extends AbstractDTO {
    private String name;
    private String website;

    public InterviewToolDTO(int i, String website) {
    }

    public HttpStatus getStatusCode() {
        return null;
    }
}