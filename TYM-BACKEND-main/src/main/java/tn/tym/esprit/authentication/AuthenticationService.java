package tn.tym.esprit.authentication;


import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.var;
import tn.tym.esprit.configuration.JwtService;
import tn.tym.esprit.entities.Booking;
import tn.tym.esprit.entities.Client;
import tn.tym.esprit.entities.Specialist;
import tn.tym.esprit.entities.User;
import tn.tym.esprit.entities.Workshop;
import tn.tym.esprit.repositories.ClientRepository;
import tn.tym.esprit.repositories.SpecialistRepository;
import tn.tym.esprit.repositories.UserRepository;

import static tn.tym.esprit.entities.RoleType.ADMIN;
import static tn.tym.esprit.entities.RoleType.CLIENT;
import static tn.tym.esprit.entities.RoleType.SPECIALIST;

import java.util.List;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;





@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final ClientRepository repositoryC;
    private final SpecialistRepository repositoryS;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = null;
        if (request.getRole()==CLIENT){
            Client client = null;

            user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(CLIENT)
                    .build();
            client = Client.builder()
                    .FirstName(request.getFirstname())
                    .Lastname(request.getLastname())
                    .email(request.getEmail())
                    .CIN(request.getCin())
                    .build();
            repository.save(user);
            repositoryC.save(client);

            System.out.print("A new CLIENT is registred");
        }
       
        else if (request.getRole()==ADMIN){
            user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(ADMIN)
                    .address("Tunisia")
                    .build();
            repository.save(user);
        }
        
        else if (request.getRole()==SPECIALIST){
            Specialist specialist = null;

            user = User.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(SPECIALIST)
                    .address(request.getAddress())
                    .isSubscribed(true)

                    .build();
            
            specialist = Specialist.builder()
                    .FirstName(request.getFirstname())
                    .LastName(request.getLastname())
                    .EmailSpecialist(request.getEmail())
                    .Bio(request.getBio())
                    .Title(request.getTitle())
                    .build();
            repository.save(user);
            repositoryS.save(specialist);
            System.out.print("A new SPECIALIST is registred");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user.get());
        System.out.print("Connected Successfully !!!  Welcome To TYM");
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//
//        var user = repository.findByEmail(request.getEmail());
//        System.out.println(request.getPassword() );
//        System.out.println(user.get().getPassword());
//        if(passwordEncoder.matches(request.getPassword(),user.get().getPassword()) ){
//            authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
//        var jwtToken = jwtService.generateToken(user.get());
//        System.out.print("Connected Successfully !!!  Welcome To COCOMARKET");
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//        }
//        else return null;
//
//    }



}
