package com.java08.quanlituyendung.service;

import com.java08.quanlituyendung.dto.CreateEventRequestDTO;
import com.java08.quanlituyendung.dto.ResponseObject;
import com.java08.quanlituyendung.dto.EventDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface IEventService {

    ResponseEntity<ResponseObject> getEvent(Long id, Authentication authentication);

    ResponseEntity<ResponseObject> deleteEvent(String eventId, Authentication authentication);

    ResponseEntity<ResponseObject> createEvent(CreateEventRequestDTO request, Authentication authentication);

    ResponseEntity<ResponseObject> getAllEvent(Authentication authentication);

    ResponseEntity<ResponseObject> updateEvent(EventDTO request, Authentication authentication);
}
