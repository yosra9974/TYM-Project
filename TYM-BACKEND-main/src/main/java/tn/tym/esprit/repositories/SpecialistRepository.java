package tn.tym.esprit.repositories;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.tym.esprit.entities.Specialist;


@Repository

public interface SpecialistRepository extends  JpaRepository <Specialist,Integer> {
	 @Query("SELECT s FROM Specialist s WHERE s.Title = :title")
	    Specialist findByTitle(String title);
	 @Query("SELECT s FROM Specialist s WHERE s.EmailSpecialist = :email")

	Optional<Specialist> findByEmail(String email);
	




	 	 

}
