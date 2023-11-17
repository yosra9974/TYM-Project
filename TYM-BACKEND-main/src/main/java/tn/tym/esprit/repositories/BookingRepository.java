package tn.tym.esprit.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.tym.esprit.entities.Booking;


@Repository
public interface BookingRepository  extends JpaRepository <Booking, Long> {

	
	
	
	
	int countBySpecialistIdSpecialist(Long idSpecialist);
	@Query("SELECT Count(r) FROM Booking r WHERE r.specialist.idSpecialist = :idSpecialist") 
    List<Booking> findBySpecialistId(Long idSpecialist);





}
