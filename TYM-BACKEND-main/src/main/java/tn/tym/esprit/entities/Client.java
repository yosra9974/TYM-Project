package tn.tym.esprit.entities;


import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Client  implements java.io.Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @JsonProperty("id") 
	    private Long idClient;
	    private String FirstName;
	    private String Lastname;
	    private String email;
	    private String CIN;
	    private String workshop;
	    @JsonFormat(pattern = "yyyy-mm-dd")

	    private Date rdvDay;
	    @OneToMany
	    private List<Booking> bookings;
	   
	    @OneToMany
	    private List<Workshop> workshops;
	    @ManyToMany
	    @JsonIgnore
	    @ToString.Exclude
	    private List<User> users;
	    @ManyToOne
	    @JoinColumn(name = "specialist_id") 
	    private Specialist specialist;
}
