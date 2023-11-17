package tn.tym.esprit.utils;

import javax.validation.constraints.NotBlank;

import lombok.Data;


//import javax.validation.constraints.NotBlank;

@Data
public class ForgotPasswordDto {
    @NotBlank
    private String email;
}
