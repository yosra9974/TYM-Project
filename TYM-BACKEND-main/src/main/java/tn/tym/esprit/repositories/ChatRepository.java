package tn.tym.esprit.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.tym.esprit.entities.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer>  {
}
