package tn.tym.esprit.services;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import tn.tym.esprit.entities.Email;
import tn.tym.esprit.repositories.EmailRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MailBoxServiceImpl {
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private UserServiceImpl userService;

    public void sendEmail(String recipient, String subject, String body, String username, String password) throws MessagingException {
        JavaMailSender javaMailSender = getJavaMailSender(username, password);

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(recipient);
        helper.setSubject(subject);
        helper.setText(body, true);

        javaMailSender.send(message);

        Email email = new Email();
        email.setTo(recipient);
        email.setSubject(subject);
        email.setBody(body);
        email.setFrom(username);
        email.setSentDate(LocalDateTime.now());

        emailRepository.save(email);
    }

    private JavaMailSender getJavaMailSender(String username, String password) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return javaMailSender;
    }

    public List<Email> receiveEmails(String username, String password) throws MessagingException, IOException {
        // Créer une session de réception d'e-mails en utilisant les informations d'authentification
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        Session session = Session.getDefaultInstance(properties, null);
        Store store = session.getStore("imaps");
        store.connect("imap.gmail.com", username, password);

        // Ouvrir le dossier de boîte de réception
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        // Récupérer les adresses e-mail des utilisateurs
        List<String> userEmais = userService.getAllUserEmails();

        // Lire les messages dans la boîte de réception
        Message[] messages = inbox.getMessages();
        List<Email> emails = new ArrayList<>();
        for (Message message : messages) {
            // Vérifier si l'e-mail est destiné à un utilisateur présent dans la base de données
            boolean isToValidUser = false;
            Address[] recipients = message.getRecipients(Message.RecipientType.TO);
            if (recipients != null) {
                for (Address recipient : recipients) {
                    if (recipient instanceof InternetAddress) {
                        String recipientEmail = ((InternetAddress) recipient).getAddress();
                        if (userEmais.contains(recipientEmail)) {
                            isToValidUser = true;
                            break;
                        }
                    }
                }
            }

            // Extraire les informations de l'e-mail et les stocker dans un objet Email si l'e-mail est destiné à un utilisateur présent dans la base de données
            if (isToValidUser) {
                Email email = new Email();
                email.setFrom(Arrays.toString(message.getFrom()));
                email.setTo(Arrays.toString(message.getRecipients(Message.RecipientType.TO)));
                email.setSubject(message.getSubject());

                // Extraire le corps du message en fonction de son type
                String contentType = message.getContentType();
                if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                    email.setBody(message.getContent().toString());
                } else if (contentType.contains("multipart")) {
                    Multipart multipart = (Multipart) message.getContent();
                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart bodyPart = multipart.getBodyPart(i);
                        if (bodyPart.getContentType().contains("text/plain") || bodyPart.getContentType().contains("text/html")) {
                            email.setBody(bodyPart.getContent().toString());
                        }
                    }
                }

                // Ajouter l'objet Email à la liste d'e-mails
                emails.add(email);
            }
        }

        // Fermer la boîte de réception et la session de réception d'e-mails
        inbox.close(false);
        store.close();

        return emails;
    }

    public List<Email> retrieveAllEmails() {
       return emailRepository.findAll();
    }
}
