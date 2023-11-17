package tn.tym.esprit.controllers;

import java.io.IOException;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import tn.tym.esprit.authentication.AuthenticationRequest;
import tn.tym.esprit.entities.Booking;
import tn.tym.esprit.entities.Specialist;
import tn.tym.esprit.entities.User;
import tn.tym.esprit.entities.Workshop;
import tn.tym.esprit.interfaces.ISpecialistService;
import tn.tym.esprit.repositories.BookingRepository;
import tn.tym.esprit.repositories.SpecialistRepository;


@RestController
@RequestMapping("/Specialists")
public class SpecialistController {
	
	@Autowired
	SpecialistRepository sr;
	@Autowired
 ISpecialistService sv;
	@Autowired
	
	
	BookingRepository bookrepo;
	

	
	 @GetMapping("/getAllSpecialists")
	    public List<Specialist> getAllSpecialists() {
	        return sv.getAllSpecialists();
	    }
	 @GetMapping("/getAllSpecialistTitles")
	 public List<String> getAllSpecialistTitles() {
	     return sr.findAll().stream().map(Specialist::getTitle).collect(Collectors.toList());
	 }
	 @GetMapping("/getSpecialistsBy/{id}")
	    public Optional<Specialist> getSpecialistById(@PathVariable Integer id) {
	        return sv.getSpecialistById(id);
	    }



	    @PutMapping("/Update-specialist/{id}")
	    public Specialist updateSpecialist(@PathVariable Long id, @RequestBody Specialist specialist) {
	        return sv.updateSpecialist(specialist);
	    }

	    @DeleteMapping("/delete-Specialist/{id}")
	    public void deleteSpecialistById(@PathVariable Integer id) {
	    	sv.deleteSpecialist(id);
	    }

	    @GetMapping("/email/{email}")
	    public Specialist getspecialistByEmail(@PathVariable String email) {
	        return sr.findByEmail(email).get();
	    }

}




