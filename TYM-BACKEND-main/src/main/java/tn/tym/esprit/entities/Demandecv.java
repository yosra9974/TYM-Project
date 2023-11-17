package tn.tym.esprit.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "DemandeCv")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Demandecv implements java.io.Serializable{
		
		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Id
		    @GeneratedValue(strategy = GenerationType.IDENTITY)
		    @JsonProperty("id") 
		  private Long idCv;
	 @Lob
	    @Column(columnDefinition = "BLOB")
	    private byte[] cv;
}
