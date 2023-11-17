package tn.tym.esprit.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.tym.esprit.services.EmailServiceImpl;



@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = "http://localhost:4200")
public class MailRestController {
    @Autowired
    private EmailServiceImpl emailService;

   @PostMapping("/send-from-TYM")
    public void sendMail(@RequestParam("to") String to,
                         @RequestParam("subject") String subject, @RequestParam("body") String body) {
       System.out.println("mail en cours");
       emailService.sendSimpleEmail(to, subject, body);
       System.out.println("mail sent");

   }
}
