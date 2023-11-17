package tn.tym.esprit.interfaces;

import java.util.List;

import tn.tym.esprit.entities.Calendar;



public interface ICalendar {

	List<Calendar> getAllEvents();

	void deleteEventById(Long eventId);

}
