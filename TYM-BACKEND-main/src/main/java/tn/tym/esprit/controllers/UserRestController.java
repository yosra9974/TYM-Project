package tn.tym.esprit.controllers;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import com.google.zxing.common.BitMatrix;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import tn.tym.esprit.authentication.AuthenticationRequest;
import tn.tym.esprit.entities.User;
import tn.tym.esprit.interfaces.IUserService;
import tn.tym.esprit.repositories.UserRepository;
import tn.tym.esprit.utils.PagingHeaders;
import tn.tym.esprit.utils.PagingResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@Slf4j
@SecurityRequirement(name = "javainuseapi")
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class UserRestController {
    @Autowired
    IUserService userService;
    @Autowired
    private UserRepository userRepository;

    ///////////////// CRUD/////////////////////////

    @GetMapping
    public List<User> retriveAllUsers() {
        List<User> listUsers = userService.retrieveAllUsers();
        return listUsers;
    }

    @GetMapping("/show-user/{id}")
    public User retrieveUser(@Valid @PathVariable("id") Integer id) {
        return userService.retrieveUser(id);
    }


//    @PostMapping("/add-user")
//    public ResponseEntity<String> addUser(@Valid @RequestBody User u) {
//        User user = userService.addUser(u);
//        return ResponseEntity.ok("User added successfully!");
//    }
@GetMapping("/getUser/{id}")
public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
    // Récupérer l'utilisateur à partir de son ID
    Optional<User> userOptional = userRepository.findById(id);

    // Vérifier si l'utilisateur existe
    if (!userOptional.isPresent()) {
        return ResponseEntity.notFound().build();
    }

    // Renvoyer l'utilisateur avec le statut 200 OK
    User user = userOptional.get();
    return ResponseEntity.ok(user);
}

    @DeleteMapping("/remove-user/{id}")
    public ResponseEntity<String> removeUser(@PathVariable("id") Integer id) {
        userService.removeUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    /*@PutMapping("/update-user")
    public ResponseEntity<String> updateUser(@Valid @RequestBody User u) {
        User user = userService.updateUser(u);
        return ResponseEntity.ok("User updated successfully!");
    }*/


    ///////////////////TRI FILTRE PAGINATION////////////
    @Transactional
    @GetMapping(value = "/recherche-avancee", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> get(
            @And({
                    @Spec(path = "firstname", params = "firstname", spec = Like.class),
                    @Spec(path = "lastname", params = "lastname", spec = Like.class),
                    @Spec(path = "email", params = "email", spec = Like.class),
                    @Spec(path = "address", params = "address", spec = Like.class),
                    @Spec(path = "fidelity", params = "fidelity", spec = Like.class),
                    @Spec(path = "role", params = "role", spec = Like.class)
            }) Specification<User> spec, Sort sort, @RequestHeader HttpHeaders headers) {
        final PagingResponse response = userService.get3(spec, headers, sort);
        return new ResponseEntity<>(response.getElements(), returnHttpHeaders(response), HttpStatus.OK);

    }

    public HttpHeaders returnHttpHeaders(PagingResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(PagingHeaders.COUNT.getName(), String.valueOf(response.getCount()));
        headers.set(PagingHeaders.PAGE_SIZE.getName(), String.valueOf(response.getPageSize()));
        headers.set(PagingHeaders.PAGE_OFFSET.getName(), String.valueOf(response.getPageOffset()));
        headers.set(PagingHeaders.PAGE_NUMBER.getName(), String.valueOf(response.getPageNumber()));
        headers.set(PagingHeaders.PAGE_TOTAL.getName(), String.valueOf(response.getPageTotal()));
        return headers;
    }

    /////////////Image Upload/////////////
    @PostMapping(value = "/uploadLogo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadLogo(@RequestParam("file") MultipartFile file, AuthenticationRequest authentication) {
        User user = userRepository.findByEmail(authentication.getEmail()).get();

        try {
            user.setLogo(file.getBytes());
            userRepository.save(user);
            return ResponseEntity.ok("cv uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload cv");
        }
    }

    @GetMapping("/{email}/logo")
    public ResponseEntity<byte[]> getUserLogo(@PathVariable String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || user.getLogo() == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(user.getLogo().length);
        return new ResponseEntity<>(user.getLogo(), headers, HttpStatus.OK);
    }

    ////////////////QR Code/////////////////
    @GetMapping("/qrcode")
    public ResponseEntity<byte[]> generateQrCode(@RequestParam("data") String data,
                                                 @RequestParam(value = "format", defaultValue = "png") String format)
            throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        int width = 300;
        int height = 300;
        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height);
        MatrixToImageWriter.writeToStream(matrix, format, stream);
        byte[] imageBytes = stream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(imageBytes.length);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email).get();
    }

}
