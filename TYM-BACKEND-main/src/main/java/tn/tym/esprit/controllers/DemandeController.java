package tn.tym.esprit.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import tn.tym.esprit.entities.Demandecv;
import tn.tym.esprit.entities.Specialist;
import tn.tym.esprit.repositories.DemandeInterface;

@RestController
@RequestMapping("/DemandeCv")
@Slf4j
@SecurityRequirement(name = "javainuseapi")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class DemandeController {
	
	@Autowired
	DemandeInterface demandeInterface;
	
	
@PostMapping(value = "/uploadCv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	
    public ResponseEntity<String> uploadCv(@RequestParam("file") MultipartFile file) {
     
		try {
		        Demandecv demande = new Demandecv();
		        demande.setCv(file.getBytes());
				demandeInterface.save(demande);
		        return ResponseEntity.ok("Cv uploaded successfully!");
		    } catch (IOException e) {
		        e.printStackTrace();
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload Cv");
		    }
	}

}
