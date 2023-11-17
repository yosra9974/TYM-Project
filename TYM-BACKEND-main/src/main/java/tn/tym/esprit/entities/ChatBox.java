package tn.tym.esprit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore
;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor /*constructeur vide*/
@AllArgsConstructor /*constructeur avec tous les attributs*/
@ToString
@Builder
public class ChatBox implements java.io.Serializable{
    @Id
    private Integer id;
    private Integer nombrePartcipants;
    private LocalDate date;
    @OneToMany(cascade = CascadeType.ALL,mappedBy="chatBox")
    @JsonIgnore
    @ToString.Exclude
    private List<Chat> chats =new ArrayList<Chat>();
    @ManyToMany
    @JsonIgnore
    @ToString.Exclude
    private List<User> users;
}
