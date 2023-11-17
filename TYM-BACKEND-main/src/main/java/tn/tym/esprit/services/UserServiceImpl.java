package tn.tym.esprit.services;



import lombok.extern.slf4j.Slf4j;
import tn.tym.esprit.entities.RoleType;
import tn.tym.esprit.entities.User;
import tn.tym.esprit.interfaces.IUserService;
import tn.tym.esprit.repositories.UserRepository;
import tn.tym.esprit.utils.PagingHeaders;
import tn.tym.esprit.utils.PagingResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserRepository ur;

    //CRUD
    @Override
    public List<User> retrieveAllUsers() {
        return ur.findAll();
    }
    @Override
    public User retrieveUser(Integer id) {
        return ur.findById(id).orElse(null);
    }
    @Override
    public User updateUser(User user) {
        System.out.print("Information(s) successfully updated");
        return ur.save(user);
    }
    @Override
    public User addUser(User user) {
        if (user.getFirstname()==null || user.getLastname()==null || user.getEmail()==null || user.getPassword()==null )
        {
            System.out.print("Missing Informations");
            return null;
        }else if (user.getEmail().matches("(.*)@(.*)")==false)

        {
            System.out.print("Enter a valid email address");
            return null;
        }else if (ur.findByEmail(user.getEmail())!=null){
            System.out.print("Already existing account");
            return null;
        }
        System.out.print("Account successfully created");

        return ur.save(user);
    }
    @Override
    public void removeUser(Integer id) {
        if ( retrieveUser(id)==null)
        {
            System.out.print("USER DOES NOT EXISTS");

        }
        ur.deleteById(id);
    }

    ///////////////////PAGINATION FILTRE ET TRI////////////////
    public PagingResponse get3(Specification<User> spec, HttpHeaders headers, Sort sort) {
        if (isRequestPaged(headers)) {
            return get(spec, buildPageRequest(headers, sort));
        } else {
            final List<User> entities = get(spec,sort);
            return new PagingResponse((long) entities.size(), 0L, 0L, 0L, 0L, entities);
        }
    }

    public List<User> get(Specification<User> spec, Sort sort) {
        return ur.findAll(spec,sort);
    }
    private boolean isRequestPaged(HttpHeaders headers) {
        return headers.containsKey(PagingHeaders.PAGE_NUMBER.getName()) && headers.containsKey(PagingHeaders.PAGE_SIZE.getName());
    }
    private Pageable buildPageRequest(HttpHeaders headers, Sort sort) {
        int page = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_NUMBER.getName())).get(0));
        int size = Integer.parseInt(Objects.requireNonNull(headers.get(PagingHeaders.PAGE_SIZE.getName())).get(0));
        return PageRequest.of(page, size, sort);
    }
    public PagingResponse get(Specification<User> spec, Pageable pageable) {
        Page<User> page = ur.findAll(spec, pageable);
        List<User> content = page.getContent();
        return new PagingResponse(page.getTotalElements(), (long) page.getNumber(), (long) page.getNumberOfElements(), pageable.getOffset(), (long) page.getTotalPages(), content);
    }

    ////////////reset password ////////////////////
    public void updatePassword(Integer userId, String newPassword) {
        /*Optional<User> userOptional = ur.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(newPassword);
            ur.save(user);
        } else {
            throw new UserNotFoundException("User not found");
        }*/
    }

    public List<String> getAllUserEmails() {
        List<String> emails = new ArrayList<>();
        List<User> users = ur.findAll();
        for (User user : users) {
            emails.add(user.getEmail());
        }
        return emails;
    }
    public List<User> getUserByRole(String role) {
        return ur.findByRole(RoleType.valueOf(role));
    }





}
