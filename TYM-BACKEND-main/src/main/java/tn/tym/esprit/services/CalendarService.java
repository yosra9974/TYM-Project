package tn.tym.esprit.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.tym.esprit.entities.Calendar;
import tn.tym.esprit.interfaces.ICalendar;
import tn.tym.esprit.repositories.CalendarBookingRepository;



@Service
public class CalendarService implements ICalendar {

	
	@Autowired
	CalendarBookingRepository calendarRepository;
	
	    
@Override
	    public List<Calendar> getAllEvents() {
	        return calendarRepository.findAll();
	    }
@Override
	    public void deleteEventById(Long eventId) {
	    	calendarRepository.deleteById(eventId);
	    }
}
