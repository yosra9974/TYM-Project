package tn.tym.esprit.controllers;

import java.time.LocalDateTime;
import java.util.Date;




import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import tn.tym.esprit.authentication.AuthenticationRequest;
import tn.tym.esprit.entities.Booking;
import tn.tym.esprit.entities.Client;
import tn.tym.esprit.entities.Specialist;
import tn.tym.esprit.interfaces.ISpecialistService;
import tn.tym.esprit.repositories.BookingRepository;
import tn.tym.esprit.repositories.ClientRepository;
import tn.tym.esprit.repositories.SpecialistRepository;





@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "http://localhost:4200")

public class BookingController {
    
   
    @Autowired
    BookingRepository bookrepo;
    @Autowired
    ClientRepository cr;
    @Autowired
    ISpecialistService sr;
    @Autowired
   SpecialistRepository srp;
   
   
 
    @PostMapping("/addRDVAndAssignCLIENT/{emailClient}/{idSpe}")
    public ResponseEntity<String> addRDVAndAssignSPCAndCLIENT(@RequestBody Booking booking, @PathVariable Integer idSpe, @PathVariable String emailClient, AuthenticationRequest authentication) {
    
    	
    	 Client client = cr.findByEmail(emailClient);
    	 Specialist specialist = srp.findById(idSpe).orElse(null);
         
        booking.setClient(client);
        booking.setSpecialist(specialist);
        bookrepo.save(booking);

        
    	
    	 return ResponseEntity.ok("Rendez-vous ajouté et spécialiste et client attribués avec succès.");  
    	 
    	
    	 }
    @GetMapping("/bookings/{idSpecialist}")
    public ResponseEntity<List<Booking>> getBookingsBySpecialistId(@PathVariable Long idSpecialist) {
        List<Booking> bookings = bookrepo.findBySpecialistId(idSpecialist);
        return ResponseEntity.ok(bookings);
    }
    
    @GetMapping("/getAllBookings")
    public List<Booking> getAllBookings() {
        return bookrepo.findAll();
    }

    @PutMapping("/Update-booking/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking ) {
        return bookrepo.save(booking);
    }

    @PutMapping("/approve-booking/{id}")
    public ResponseEntity<String> approveBooking(@PathVariable Long id) {
        Optional<Booking> optionalBooking = bookrepo.findById(id);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.setApproved(true);
            bookrepo.save(booking);
            return ResponseEntity.ok("Booking with ID " + id + " has been approved.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Booking with ID " + id + " not found.");
        }
    }

    @PutMapping("/deny-booking/{id}")
    public void denyBooking(@PathVariable Long id) {
        System.out.println(id+"--------------");
        bookrepo.deleteById(id) ;
    }

    @DeleteMapping("/delete-Booking/{id}")
    public void deleteBookingById(@PathVariable Long id) {
    	bookrepo.deleteById(id);
    }
	

}
