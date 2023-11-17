package tn.tym.esprit.entities;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor /*constructeur vide*/
@AllArgsConstructor /*constructeur avec tous les attributs*/
@ToString
public class Calendar {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dateDebut;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime dateFin;

	@JsonProperty("eventType")
	private String eventType; // Add event type field

	@JsonProperty("eventName")
	private String eventName; // Add event type field

	@ManyToOne(cascade = CascadeType.ALL)
	@ToString.Exclude
	private User user;
}
