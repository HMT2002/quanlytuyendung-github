package com.java08.quanlituyendung.controller;

import com.google.api.services.calendar.model.Event;
import com.java08.quanlituyendung.calendar.CalendarService;
import com.java08.quanlituyendung.dto.*;
import com.java08.quanlituyendung.service.IEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    @Autowired
    IEventService eventService;

    @Autowired
    CalendarService calendarService;

    @GetMapping
    public ResponseEntity<ResponseObject> getAllEvent(Authentication authentication) {
        return eventService.getAllEvent(authentication);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getEvent(@PathVariable Long id, Authentication authentication) {
        return eventService.getEvent(id, authentication);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createEvent(@RequestBody CreateEventRequestDTO request, Authentication authentication) {
        return eventService.createEvent(request, authentication);
    }

    @PutMapping
    public ResponseEntity<ResponseObject> updateEvent(@RequestBody EventDTO request, Authentication authentication){
        return eventService.updateEvent(request, authentication);
    }

    @DeleteMapping
    public ResponseEntity<ResponseObject> delete(@RequestParam String eventId, Authentication authentication) {
        return eventService.deleteEvent(eventId, authentication);
    }

    @PostMapping("/calendar")
    public Event calendar(@RequestBody CalendarAddRequestDTO requestDTO) throws IOException, GeneralSecurityException {
        return calendarService.createEvent(requestDTO);
    }
}
