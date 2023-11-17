package tn.tym.esprit.services;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.tym.esprit.entities.Specialist;
import tn.tym.esprit.entities.Workshop;
import tn.tym.esprit.interfaces.ISpecialistService;
import tn.tym.esprit.repositories.SpecialistRepository;



@Service
public class SpecialistService implements ISpecialistService {
	
	 @Autowired
	 SpecialistRepository  sr;

	 @Override
	    public Specialist addSpecialists( Specialist  specialist) {
	   
	        return sr.save(specialist);
	    }
	 	 @Override
	    public void deleteSpecialist(Integer id) {
		 sr.deleteById(id);
	    }
		@Override
	    public List<Specialist> getAllSpecialists() {
			List<Specialist> allSpe =  sr.findAll();
			return allSpe;
	    }
		@Override
		public Optional<Specialist> getSpecialistById(Integer id) {
			
			return sr.findById(id);
		}
		
		  @Override

		    public Specialist updateSpecialist(Specialist specialist) {
		        return sr.save(specialist);
		    }
		}
