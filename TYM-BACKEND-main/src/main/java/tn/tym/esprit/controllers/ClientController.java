package tn.tym.esprit.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.tym.esprit.entities.Client;
import tn.tym.esprit.entities.Specialist;
import tn.tym.esprit.repositories.ClientRepository;


@RestController
@RequestMapping("/Clients")
@CrossOrigin(origins = "http://localhost:4200")
public class ClientController {
	   @Autowired
	    ClientRepository cr;
	   

	    @GetMapping("/getAllClients")
	    public List<Client> getAllClients() {
	        return cr.findAll();
	    }
	    @GetMapping("/email/{email}")
	    public Client getsClientByEmail(@PathVariable String email) {
	        return cr.findByEmail(email);
	    }



}
