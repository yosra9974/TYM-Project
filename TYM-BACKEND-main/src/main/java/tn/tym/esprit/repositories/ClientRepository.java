package tn.tym.esprit.repositories;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.tym.esprit.entities.Client;


@Repository
public interface ClientRepository extends  JpaRepository <Client,Long> {
	  @Query("SELECT e FROM Client e WHERE e.email = :email")
	  Client findByEmail(@Param("email") String email);
	 
		
}
