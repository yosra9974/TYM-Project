package tn.tym.esprit.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.tym.esprit.entities.Email;


@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {

}
