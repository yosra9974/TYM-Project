package tn.tym.esprit.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.tym.esprit.entities.MessageContent;


@RestController
@RequestMapping("/notification/message")
@CrossOrigin(origins = "http://localhost:4200")

public class WebSocketController {
	@MessageMapping("/sendMessage")
    @SendTo("/topic/message")
    public MessageContent sendMessage(MessageContent messageContent) {
        return messageContent;
    }
}
