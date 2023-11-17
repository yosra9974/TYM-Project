package tn.tym.esprit.interfaces;

import tn.tym.esprit.entities.ChatBox;

public interface IChatBoxService {
    ChatBox addChatBox(ChatBox chatBox);
    ChatBox retrieveBoite(Integer id1, Integer id2);
}
