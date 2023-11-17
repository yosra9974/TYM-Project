package tn.tym.esprit.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.access.prepost.PreAuthorize;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "Specialist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class Specialist  implements java.io.Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @JsonProperty("id") 
	    private Integer idSpecialist;
	    private String Photo;
	    private Integer Rating;
	    private String FirstName;
	    private String LastName;
	    private String EmailSpecialist;
	    private String workshop;

	    private String Title;
	    private String Bio;
		@JsonFormat(pattern = "yyyy-mm-dd")

	    private Date RdvDemand;
		@JsonFormat(pattern = "yyyy-mm-dd")
	    private Date rdv;



	   
	    @Lob
	    @Column(columnDefinition = "BLOB")
	    private byte[] cv;
	    
	    @OneToMany
	    private List<Booking> bookings;
	    @ManyToMany
	    @JsonIgnore
	    @ToString.Exclude
	    private List<User> users;
	    @OneToMany
	    private List<Workshop> workshops;
}
