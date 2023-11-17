package tn.tym.esprit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.tym.esprit.entities.Demandecv;

@Repository

public interface DemandeInterface  extends  JpaRepository <Demandecv,Long>{

}
