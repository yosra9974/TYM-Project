
package tn.tym.esprit.authentication;


import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.tym.esprit.entities.StatusUser;
import tn.tym.esprit.entities.User;
import tn.tym.esprit.interfaces.IUserService;
import tn.tym.esprit.repositories.UserRepository;
import tn.tym.esprit.services.EmailServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static javax.crypto.Cipher.SECRET_KEY;


@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    private final AuthenticationService service;
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private static final String SECRET_KEY = "70337336763979244226452948404D635166546A576E5A7234743777217A2543";
    @Autowired
    private ForgotPasswordService pwdservice;
    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EmailServiceImpl emailService;
    private static ScheduledFuture scheduledFuture;
private static int code;
private void clearCode (){code=0;System.out.println("cleared");}
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        emailService.sendSimpleEmail(request.getEmail(),
                "Welcome Mail",
                "Welcome To TYM"+
                "\n" +
                "Bienvenue à TYM,\n" +
                "\n" +
                "Merci d’avoir rejoint TYM.\n" +
                "\n" +
                "Nous aimerions vous confirmer que votre compte a été créé avec succès.\n "+
                "\n" +
                "Si vous rencontrez des difficultés pour vous connecter à votre compte, contactez-nous à noreply.pidev@gmail.com.\n" +
                "\n" +
                "Cordialement,\n" +
                "TYM");
        System.out.print("Account successfully created");
        return ResponseEntity.ok(service.register(request));
    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<AuthenticationResponse> authenticate(
//            @RequestBody AuthenticationRequest request
//    ) {
//        return ResponseEntity.ok(service.authenticate(request));
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        User user = userRepository.findByEmail(request.getEmail()).get();
        if(user.getStatusUser()==StatusUser.BANNED){
            System.out.print("YOUR ACCOUNT IS BANNED");
            return ResponseEntity.ok("YOUR ACCOUNT IS BANNED");
        }
        if(user.getStatusUser()==null){
            System.out.print("YOUR ACCOUNT IS NOT VERIFIED");
            return ResponseEntity.ok("YOUR ACCOUNT IS NOT VERIFIED");
        }
        AuthenticationResponse auth = service.authenticate(request);
        if(auth==null)
            return null;
        else
            return ResponseEntity.ok(service.authenticate(request));
    }



    /*@PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        try {
            String newPassword = pwdservice.generateNewPassword();
            pwdservice.sendPasswordResetEmail(email, newPassword);

            User user = userRepository.findByEmail(email).get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.updateUser(user);

            return ResponseEntity.ok("Password reset email sent");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found");
        }
    }*/
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        try {
            String newPassword = pwdservice.generateNewPassword();
            pwdservice.sendPasswordResetEmail(email, newPassword);

            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found");
            }
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.updateUser(user);

            return ResponseEntity.ok("Password reset email sent");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found");
        }
    }


    @PostMapping("/update-password")
    public ResponseEntity<?>  updatePassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        User user = userRepository.findByEmail(email).get();
        boolean isMatch = passwordEncoder.matches(oldPassword, user.getPassword());
        if (isMatch) {
            String encodedNewPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedNewPassword);
            userRepository.save(user);
            return ResponseEntity.ok("Password updated successfully!");
        } else {
            return ResponseEntity.ok(" Please enter the right password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return ResponseEntity.ok("User logged out successfully.");
    }
    /*@RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Get the authorization header from the request
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                // Extract the token from the authorization header
                String token = authorizationHeader.substring(7);

                // Invalidate the token by setting its expiration time to a past date
                Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token);
                Date expirationTime = new Date(System.currentTimeMillis() - 1000);
                claims.getBody().setExpiration(expirationTime);

                // Add the expired token to the response headers to notify the client to delete it
                response.setHeader("Authorization", "Bearer " + token);

                return new ResponseEntity<>("Logout successful", HttpStatus.OK);
            } catch (JwtException e) {
                return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Authorization header missing or invalid", HttpStatus.BAD_REQUEST);
        }
    }*/


    @PostMapping("/send-code")
    public boolean sendCode(@RequestBody String email) {

        User user = userRepository.findByEmail(email).get();
        if (user==null){
            return false;
        }
        // générez le code ici
        Random random = new Random();
        code = 100000 + random.nextInt(900000);
        userRepository.save(user);
        if (scheduledFuture!=null)
        {scheduledFuture.cancel(true);}
        scheduledFuture = executorService.schedule(() -> clearCode(), 1, TimeUnit.MINUTES);
        emailService.sendSimpleEmail(email, "Cocomarket Verification Code", String.valueOf(code));
        System.out.println(code);
        return true;
    }

    /*@PostMapping("/verify-code")
    public void verifyCode(@RequestBody String email,@RequestBody int code) {
        User user = userRepository.findByEmail(email).get();
        // vérifiez le code ici
       if(code==user.getCode()){
           user.setStatusUser(StatusUser.ACTIVE);
           userRepository.save(user);
           //return ResponseEntity.ok("We are pleased to inform you that your account has been successfully verified");
       }
        //return ResponseEntity.ok("Unfortunately, we cannot verify your account at this time, " +
                //"as we have encountered issues with the information provided");
    }*/

    @PostMapping("/verify-code")
    //version adéquate Angular
    public boolean verifyCode(@RequestBody Map<String, Object> requestBody) {
        String email = (String) requestBody.get("email");
        int code2 = (int) requestBody.get("code");

        User user = userRepository.findByEmail(email).get();

        if (code == code2) {
            user.setSubscribed(true);
            user.setStatusUser(StatusUser.ACTIVE);
            userRepository.save(user);
            return true;
        }
        return false;
    }




}
