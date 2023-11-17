package tn.tym.esprit.services;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import tn.tym.esprit.entities.ChatBox;
import tn.tym.esprit.entities.User;
import tn.tym.esprit.interfaces.IChatBoxService;
import tn.tym.esprit.repositories.ChatBoxRepository;
import tn.tym.esprit.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class IChatBoxServiceImpl implements IChatBoxService {
    @Autowired
    ChatBoxRepository chatBoxrepo;
    @Autowired
    UserRepository userRepository;
    @Override
    public ChatBox addChatBox(ChatBox chatBox){
        return chatBoxrepo.save(chatBox);
    }
    @Override
    public ChatBox retrieveBoite(Integer id1, Integer id2){
        User user1 = userRepository.findById(id1).get();
        User user2 = userRepository.findById(id2).get();
        List<User> users = new ArrayList<>();
        users.add(user1);users.add(user2);
        List<ChatBox> chatBoxes = chatBoxrepo.findAll();
        ChatBox boite = chatBoxes.stream()
                .filter(cb -> cb.getId().equals("channel-" + id1 + "-" + id2) || cb.getId().equals("channel-" + id2 + "-" + id1))
                .findFirst()
                .orElse(null);
        return boite;
    }
}
