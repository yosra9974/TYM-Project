package tn.tym.esprit.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.tym.esprit.entities.Workshop;

@Repository
public interface WorkshopRepository  extends JpaRepository<Workshop,Integer> {
}
