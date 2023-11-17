package tn.tym.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import tn.tym.esprit.entities.Calendar;

import java.util.List;


@Repository
public interface CalendarBookingRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findByUserId(Integer user_id);
}
