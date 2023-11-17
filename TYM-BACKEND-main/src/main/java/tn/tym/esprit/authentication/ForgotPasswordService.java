package tn.tym.esprit.authentication;


import lombok.RequiredArgsConstructor;

import tn.tym.esprit.entities.User;
import tn.tym.esprit.interfaces.IUserService;
import tn.tym.esprit.repositories.UserRepository;
import tn.tym.esprit.services.EmailServiceImpl;
import tn.tym.esprit.utils.ForgotPasswordDto;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private EmailServiceImpl emailService;

    public void resetPassword(ForgotPasswordDto forgotPasswordDto) {
        User user = userRepository.findByEmail(forgotPasswordDto.getEmail()).get();
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        // generate new password
        String newPassword = generateNewPassword();

        // update user password
        userService.updatePassword(user.getId(), newPassword);

        // send password reset email
        sendPasswordResetEmail(user.getEmail(), newPassword);
    }

    String generateNewPassword() {
        // generate new password
        //random password that consists of 10 alphanumeric characters
        String newPassword = RandomStringUtils.randomAlphanumeric(10);
        return newPassword;
    }

    void sendPasswordResetEmail(String email, String newPassword) {
        // send password reset email
        emailService.sendSimpleEmail(email, "Password Reset","Your new password is: " + newPassword);
    }
}
