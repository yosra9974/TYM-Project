package tn.tym.esprit.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.tym.esprit.entities.BanUser;
import tn.tym.esprit.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface BanUserRepository extends JpaRepository<BanUser, Integer> {
    List<BanUser> findByUser(User user);
}

