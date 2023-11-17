package tn.tym.esprit.controllers;


import io.jsonwebtoken.Claims;
import tn.tym.esprit.configuration.JwtService;
import tn.tym.esprit.entities.ChatBox;
import tn.tym.esprit.entities.User;
import tn.tym.esprit.interfaces.IChatBoxService;
import tn.tym.esprit.repositories.ChatBoxRepository;
import tn.tym.esprit.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@CrossOrigin(origins = {"*"})
@RestController
public class ChatBoxRestController {
    @Autowired
    IChatBoxService IChatBoxservice;

    private final JwtService jwtService ;
    private final UserRepository userRepository;
    private final ChatBoxRepository chatBoxRepository;

    public ChatBoxRestController(JwtService jwtService,
                                 UserRepository userRepository,
                                 ChatBoxRepository chatBoxRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.chatBoxRepository = chatBoxRepository;
    }


    @PostMapping("/addChatBox")
    public ResponseEntity<String> addChatBox(@Valid @RequestBody ChatBox chatBox) {
        ChatBox c = IChatBoxservice.addChatBox(chatBox);
        return ResponseEntity.ok("Chat Box added successfully!");
    }
    @GetMapping("/retrieveBoite/user/{token}/chat/{id2}")
    public ResponseEntity<ChatBox> retrieveBoite(@Valid @PathVariable("token") String token, @Valid @PathVariable("id2") Integer id2) {
        Claims claim =  jwtService.decodeToken(token);
        User user = userRepository.findByEmail(claim.get("sub",String.class)).get();

        ChatBox chatBox =  IChatBoxservice.retrieveBoite(user.getId(), id2);
        if (chatBox != null) {
            return ResponseEntity.ok(chatBox);
        } else {
            ChatBox chatBoxN = new ChatBox();
            chatBoxN.setId(Integer.valueOf("channel-"+user.getId()+"-"+id2));
            chatBoxN.setDate(LocalDate.now());
            chatBoxN.setNombrePartcipants(2);
            chatBoxRepository.save(chatBoxN);
            return ResponseEntity.ok(chatBoxN);
        }
}
}