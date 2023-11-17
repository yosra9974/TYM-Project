package tn.tym.esprit.controllers;

import java.sql.Date;
import java.sql.SQLOutput;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tn.tym.esprit.entities.Calendar;
import tn.tym.esprit.entities.User;
import tn.tym.esprit.interfaces.ICalendar;
import tn.tym.esprit.interfaces.IUserService;
import tn.tym.esprit.repositories.CalendarBookingRepository;

@RestController
@RequestMapping("/api")
public class CalendarController {
    @Autowired
    CalendarBookingRepository calendarBookingRepository;

    @Autowired
    ICalendar calendarService;

    @Autowired
    IUserService userService;

    @GetMapping("/getCalendarEvents")
    public ResponseEntity<List<Calendar>> getCalendarEvents() {
        List<Calendar> events = calendarBookingRepository.findAll();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/getCalendarEvent/{id}")
    public ResponseEntity<List<Calendar>> getCalendarEvents(@PathVariable int id) {
        List<Calendar> events = calendarBookingRepository.findByUserId(id);
        System.out.print("------------------------------"+events.toString());
        return ResponseEntity.ok(events);
    }

    @PostMapping("/addCalendarEvent/{id}")
    public Calendar addCalendarEvent(@RequestBody Calendar event, @PathVariable("id") Integer id) {
        System.out.print(event + "+++++++++++++++");
        User user = userService.retrieveUser(id);
        event.setUser(user);
        return calendarBookingRepository.save(event);
    }
}
