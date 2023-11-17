package tn.tym.esprit.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import tn.tym.esprit.entities.ChatBox;

@EnableJpaRepositories
public interface ChatBoxRepository extends JpaRepository<ChatBox, String> {
}