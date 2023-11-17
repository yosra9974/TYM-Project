package tn.tym.esprit.authentication;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.tym.esprit.entities.RoleType;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private RoleType role;
    private String address;
    private String photo;
    private String cin;
    private String bio;
    private String title;
}
