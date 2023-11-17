package tn.tym.esprit.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class VendorMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Autowired
    EmailServiceImpl emailService;

    public void sendTenderMail(String toEmail, String subject, String body) {
        /*SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Tender Mail Sent successfully to " + toEmail);*/
        emailService.sendSimpleEmail(toEmail,subject,body);
    }

    public void sendQuantityLowEmail( String vendorMail) {
        String subject = "Product Quantity Low";
        String body = "Greetings, one of your products is running out of quantity. Please click on the link below to start a tender for it:\n\n";
        String tenderLink = "http://localhost:8075/swagger-ui/index.html#/tender-controller/addTender";
        body += tenderLink;
       /* SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ameni.belhadj@esprit.tn");
        message.setTo(vendorMail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Sent successfully !! ");*/
        emailService.sendSimpleEmail(vendorMail,subject,body);
    }

}
