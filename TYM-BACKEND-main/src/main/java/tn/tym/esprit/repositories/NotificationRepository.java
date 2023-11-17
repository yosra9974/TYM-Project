package tn.tym.esprit.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import tn.tym.esprit.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Integer > {



}
