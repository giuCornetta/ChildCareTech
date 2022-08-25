package it.polimi.inginf.childcaretech.security;

import it.polimi.inginf.childcaretech.data.Staff;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationForm {

    //TODO logout
    @NotBlank(message = "Username is required")
    @Size(max = 100, message = "Username must be less than 100 characters long")
    private String username;
    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be less than 100 characters long")
    private String name;
    @NotBlank(message = "Surname is required")
    @Size(max = 100, message = "Surname must be less than 100 characters long")
    private String surname;
    @NotBlank(message="CF is required")
    @Size(min=16, max = 16, message = "CF must be 16 characters long")
    private String cf;
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @NotBlank(message = "Password is required")
    @Size(max = 200, message = "Password must be less than 200 characters long")
    private String password;

    @NotBlank
    private String confirm;
    //TODO confirm password? lo teniamo? come facciamo la validation?
    @NotBlank(message = "Type of member is required")
    private String type;

    @NotBlank(message = "Phone number is required")
    @Digits(integer = 10, fraction = 0, message = "Invalid phone number")
    private String telephone;

    public Staff toUser(PasswordEncoder passwordEncoder) {
        return new Staff(cf,name,surname,username,passwordEncoder.encode(password),type,telephone,email);
    }

}
