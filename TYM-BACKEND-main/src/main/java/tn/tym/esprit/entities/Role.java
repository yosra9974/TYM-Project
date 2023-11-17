package tn.tym.esprit.entities;



import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@ToString
@Builder
public class Role implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
   // @ManyToMany(mappedBy="roles",cascade =CascadeType.ALL)
   // @JsonIgnore
    //@ToString.Exclude
  //  private Set<User> users;
}
