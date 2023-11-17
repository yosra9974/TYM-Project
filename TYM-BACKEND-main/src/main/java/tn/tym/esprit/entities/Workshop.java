package tn.tym.esprit.entities;


import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@Entity
@Table(name = "workshop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Workshop  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	@JsonProperty("Name")

    private String Name;
	@JsonProperty("Time")

	private Date Time;
	@JsonProperty("Places")

	private Integer Places ;
	@JsonProperty("Online")

	private String Online ;
	@JsonProperty("Prices")

	private Integer Prices ;
	@JsonIgnore
	@ManyToOne
    @JoinColumn(name = "client_id") 
    private Client client;
	@ManyToOne
    @JoinColumn(name = "specialist_id") 
    private Specialist specialist;
	
	

}
