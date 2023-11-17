package tn.tym.esprit.interfaces;

import java.util.List;

import java.util.Optional;

import tn.tym.esprit.entities.Specialist;



public interface ISpecialistService {

	Specialist addSpecialists(Specialist specialist);


	


	List<Specialist> getAllSpecialists();


	void deleteSpecialist(Integer id);





	Optional<Specialist> getSpecialistById(Integer id);





	Specialist updateSpecialist(Specialist specialist);














	

}
