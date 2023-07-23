package com.java08.quanlituyendung.controller;

import com.google.api.services.calendar.model.Event;
import com.java08.quanlituyendung.calendar.CalendarService;
import com.java08.quanlituyendung.dto.CalendarAddRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;
    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    @PostMapping
    public Event calendar(@RequestBody CalendarAddRequestDTO requestDTO) throws IOException, GeneralSecurityException {
        return calendarService.createEvent(requestDTO);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500/")
    @GetMapping("/authen")
    public RedirectView auth(){
        String oauthUrl = "https://accounts.google.com/o/oauth2/auth?access_type=offline&client_id=854899780211-ei96d98kmmdv7aq6ghmkk7fbo58hc7b2.apps.googleusercontent.com&redirect_uri=http://localhost:8888/Callback&response_type=code&scope=https://www.googleapis.com/auth/calendar.events%20https://www.googleapis.com/auth/calendar";
        return new RedirectView(oauthUrl);
    }
}
