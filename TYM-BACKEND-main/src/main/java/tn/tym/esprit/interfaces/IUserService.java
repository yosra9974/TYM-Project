package tn.tym.esprit.interfaces;


import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

import tn.tym.esprit.entities.Specialist;
import tn.tym.esprit.entities.User;
import tn.tym.esprit.utils.PagingResponse;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> retrieveAllUsers();
    User retrieveUser (Integer id);
    User updateUser (User user);
    User addUser (User user);
    void removeUser (Integer id);
    PagingResponse get3(Specification<User> spec, HttpHeaders headers, Sort sort);
    public void updatePassword(Integer userId, String newPassword);

    List<User> getUserByRole(String role);
}
