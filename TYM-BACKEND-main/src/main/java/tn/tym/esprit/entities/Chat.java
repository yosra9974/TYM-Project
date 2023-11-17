package tn.tym.esprit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor /*constructeur vide*/
@AllArgsConstructor /*constructeur avec tous les attributs*/
@ToString
@Builder
public class Chat implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    private MessageType type;
    private String content;
    private String sender;
    private String fromm;
    private LocalDateTime date;
    private String text;
    @ManyToOne
    @ToString.Exclude
    @JsonIgnore
    private  ChatBox chatBox;
    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}
