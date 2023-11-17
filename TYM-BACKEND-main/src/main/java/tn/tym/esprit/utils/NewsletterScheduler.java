package tn.tym.esprit.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tn.tym.esprit.entities.User;
import tn.tym.esprit.repositories.UserRepository;
import tn.tym.esprit.services.EmailServiceImpl;

import java.util.List;

@Component
@EnableScheduling
public class NewsletterScheduler {
    @Autowired
    EmailServiceImpl emailService;
    @Autowired
    private UserRepository userRepository;

    //chaque lundi midi
    //@Scheduled(cron = "0 0 12 ? * MON")
    @Scheduled(cron = "0 * * * * ?")
    public void sendNewsletter() {
        List<User> subscribers = userRepository.findAllByIsSubscribed(true);
        for (User subscriber : subscribers) {
            String newsletterContent = "Dear " + subscriber.getFirstname() + ",\n\n" +
                    "We hope this email finds you well! We wanted to touch base and share the latest news and updates from ChatGPT, " +
                    "your go-to source for insightful content.\n\n" +
                    "In this week's newsletter, we have curated a collection of articles and features that we believe you'll find interesting, including:\n" +
                    "- 5 ways to boost your productivity at work\n" +
                    "- The psychology behind procrastination and how to overcome it\n" +
                    "- The benefits of practicing mindfulness for mental health\n\n" +
                    "We strive to provide you with the highest quality content that is both informative and engaging. \n\n" +
                    "We understand that your inbox can quickly become overwhelming, and we respect your time and privacy. " +
                    "If you would like to unsubscribe from our newsletter, please click on the following link: http://localhost:4200/user/unsubscribe\n\n" +
                    "Thank you for your continued support, and we look forward to keeping you informed and entertained in the weeks to come!\n\n" +
                    "Best regards,\n\n" +
                    "The Cocomarket Team";

            emailService.sendSimpleEmail(subscriber.getEmail(), "Your Weekly Newsletter from Cocomarket", newsletterContent);
        }
    }


}
