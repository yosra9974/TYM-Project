package tn.tym.esprit.controllers;



import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import tn.tym.esprit.authentication.AuthenticationRequest;
import tn.tym.esprit.entities.Client;
import tn.tym.esprit.entities.Specialist;
import tn.tym.esprit.entities.Workshop;
import tn.tym.esprit.interfaces.ISpecialistService;
import tn.tym.esprit.interfaces.IWorkshop;
import tn.tym.esprit.repositories.ClientRepository;
import tn.tym.esprit.repositories.SpecialistRepository;
import tn.tym.esprit.repositories.WorkshopRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/workshop")
@Slf4j
@SecurityRequirement(name = "javainuseapi")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class WorkshopController {
    @Autowired
    IWorkshop iw;
    @Autowired
	WorkshopRepository wr;
	@Autowired
 ISpecialistService sv;
	 @Autowired
	    ClientRepository cr;	
		@Autowired
		SpecialistRepository srp;
  
    @GetMapping("/getAllWorkshops/{id}")
    public Optional<Workshop> getWorkshopById(@PathVariable Integer id) {
        return iw.getWorkshopById(id);
    }

    @GetMapping("/showall-workshops")
    public List<Workshop> getAllWorkshops() {
        return iw.getAllWorkshops();
    }

    @PutMapping("/Updateworkshops/{id}")
    public Workshop updateWorkshop(@PathVariable Integer id, @RequestBody Workshop workshop) {
        workshop.setId(id);
        return iw.updateWorkshop(workshop);
    }

    @DeleteMapping("/delete-workshop/{id}")
    public void deleteWorkshopById(@PathVariable Integer id) {
        iw.deleteWorkshopById(id);
    }
    
    
    @PostMapping("/addWorkshopAndAssignCLIENT/{email}/{email_specialist}")
    public ResponseEntity<String> addWorkshopAndAssignCLIENT(@RequestBody Workshop workshop, @PathVariable String email , @PathVariable String email_specialist) {
    	
        Client client = cr.findByEmail(email);

        Specialist specialist = srp.findByEmail(email_specialist).orElse(null);
        
 		

        workshop.setSpecialist(specialist);
        workshop.setClient(client);

        workshop.getSpecialist().setWorkshop(workshop.getName());
        workshop.getClient().setWorkshop(workshop.getName());
   
        wr.save(workshop);
        srp.save(specialist);
        cr.save(client);

        return ResponseEntity.ok("workshop Affected");
    }

}
