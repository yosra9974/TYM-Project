package tn.tym.esprit.repositories;




import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.tym.esprit.entities.Contract;

@Repository

public interface ContractRepository extends  JpaRepository <Contract,Long> {

	    @Query("SELECT COALESCE(SUM(c.duration), 0) FROM Contract c")
	    Long getTotalDuration();
}
