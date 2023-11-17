package tn.tym.esprit.entities;

import java.sql.Date;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Booking implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id") 
    private Long id;
	@JsonProperty("firstname")
    private String firstname;
	@JsonProperty("lastname")
    private String lastname;
	@JsonProperty("online")
    private String online;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime Book;

	@ManyToOne
    @JoinColumn(name = "client_id") 
    private Client client;
	@ManyToOne
    @JoinColumn(name = "specialist_id") 
    private Specialist specialist;

	@JsonProperty("approved")
	private boolean approved;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	


}
