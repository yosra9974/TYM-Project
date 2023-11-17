package tn.tym.esprit.controllers;


import io.jsonwebtoken.Claims;
import tn.tym.esprit.configuration.JwtService;
import tn.tym.esprit.entities.Chat;
import tn.tym.esprit.entities.ChatBox;
import tn.tym.esprit.entities.User;
import tn.tym.esprit.interfaces.IChatBoxService;
import tn.tym.esprit.repositories.ChatBoxRepository;
import tn.tym.esprit.repositories.ChatRepository;
import tn.tym.esprit.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@CrossOrigin(origins = "http://localhost:4200")

public class ChatRestController {
    @Autowired
    ChatBoxRepository chatBoxrepo;
    @Autowired
    IChatBoxService iChatBoxService;
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UserRepository userRepository;
    private final JwtService jwtService ;

    public ChatRestController(JwtService jwtService,
                              UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
    @Transactional
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Chat sendMessage(@Payload Chat chat) {
        LocalDateTime now = LocalDateTime.now();
        chat.setDate(now);
        ChatBox chatBox = chatBoxrepo.findById(String.valueOf(5976)).get();
        chat.setChatBox(chatBox);
        chat.setType(Chat.MessageType.CHAT);
        List<Chat> chats = chatBox.getChats();
        chats.add(chat);
        chatBox.setChats(chats);
        chatRepository.save(chat);
        return chat;
    }

    @GetMapping("/public/retrieveAllMsg")
    public ResponseEntity<List<Chat>> retrieveAllMsg() {
        ChatBox chatBox = chatBoxrepo.findById(String.valueOf(15976)).get();



        if (chatBox.getChats().size()==0) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(chatBox.getChats().stream().sorted(Comparator.comparing(Chat::getDate))
                    .collect(Collectors.toList()));
        }
    }
    ///app/channel/"+{chatBoxId}+"/Sender/"+{token}+"/to/"+{id2}
    @Transactional
    @MessageMapping("/channel/{chatBoxId}/Sender/{token}/to/{id2}")
    @SendTo("/topic/channel/{chatBoxId}")
    public Chat sendMessageUsers(@Payload Chat chat, @DestinationVariable("chatBoxId") Integer chatBoxId, @DestinationVariable("token") String token, @DestinationVariable("id2") String id2) {
        Claims claim =  jwtService.decodeToken(token);
        User user = userRepository.findByEmail(claim.get("sub",String.class)).get();
        LocalDateTime now = LocalDateTime.now();
        chat.setDate(now);
        ChatBox chatBox = chatBoxrepo.findById(String.valueOf(chatBoxId)).get();
        chat.setChatBox(chatBox);
        chat.setFromm(id2);
        chat.setType(Chat.MessageType.CHAT);
        List<Chat> chats = chatBox.getChats();
        chats.add(chat);
        chatBox.setChats(chats);
        chatRepository.save(chat);
        return chat;
    }

    @MessageMapping("/chat.connect")
    @SendTo("/queue/reply")
    public Chat connectToChat(@Payload Chat chat, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String currentUserToken = headerAccessor.getUser().getName(); // get current user's token
        Claims claim =  jwtService.decodeToken(chat.getSender());
        User currentUser = userRepository.findByEmail(claim.get("sub",String.class)).get();
        User otherUser = userRepository.findById(Integer.parseInt(chat.getFromm())).get(); // get other user's object from database
        String chatId = "user-" + currentUser.getEmail() + "-chat-" + otherUser.getEmail();
        chat.setSender(currentUser.getEmail());
        chat.setSender(otherUser.getEmail());
        /*Chat existingChat = iChatBoxService.getChatById(chatId);
        if (existingChat == null) {
            chat.setUsers(Arrays.asList(currentUser, otherUser));
            chatService.addChat(chat); // add chat to database if it doesn't exist
        }
        List<Chat> chats = iChatBoxService.getChatsByUserId(currentUser.getId());
        return new Chat(chats); // return all chats for the current user*/
        return null;
    }




    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Chat addUser(@Payload Chat chat,
                        SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chat.getSender());
        return chat;
    }
    public ResponseEntity<String> addChat(@Valid @RequestBody Chat chat) {
        chatRepository.save(chat);
        return ResponseEntity.ok("Chat added successfully!");
    }
    @MessageMapping("/chat.createRoom")
    public void createOrGetRoom(Chat message) throws Exception {
        String sender = message.getSender();
        String to = message.getFromm();
        String roomId = sender + "-" + to;
        String channelName = "/user/" + sender + "/chat/" + to;
/*
        User user1 = userRepository.findById(Integer.parseInt(sender)).get();
        User user2 = userRepository.findById(Integer.parseInt(to)).get();
        List<User> users = new ArrayList<>();
        users.add(user1);users.add(user2);
        List<ChatBox> chatBoxes = chatBoxrepo.findAll();
        ChatBox boite= chatBoxes.stream()
                .filter(chatBox -> chatBox.getUsers().containsAll(users))
                .findFirst()
                .orElse(null);

        // Check if the room already exists
        if (boite!=null) {
            List<Chat> chats = boite.getChats();
            chats.add(message);boite.setChats(chats);
            chatBoxrepo.save(boite);
        } else {
            // If the room does not exist, create a new chat room and add the message
            ChatBox chatRoom = new ChatBox();
            List<Chat> chats = chatRoom.getChats();
            chats.add(message);chatRoom.setChats(chats);
            chatRoom.setUsers(users);
            List<ChatBox> chatBoxes1 = user1.getChatBoxes();
            chatBoxes1.add(chatRoom);
            user1.setChatBoxes(chatBoxes1);
            user2.setChatBoxes(chatBoxes1);

            chatBoxrepo.save(boite);
            chatRepository.save(message);
        }
        */
/*
        // Notify the sender and receiver that the message has been added to the room
        simpMessagingTemplate.convertAndSendToUser(sender, "/queue/chat.message", message);
        simpMessagingTemplate.convertAndSendToUser(to, "/queue/chat.message", message);*/
    }
}
