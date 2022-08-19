package it.polimi.inginf.childcaretech.security;

import it.polimi.inginf.childcaretech.Data.Staff;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class RegistrationForm {
    //TODO error se ci sono errori in alcuni vincoli
    private String username;
    private String name;
    private String surname;
    private String cf;
    private String email;

    private String password;
    //TODO confirm password?
    private String type;
    private String telephone;

    public Staff toUser(PasswordEncoder passwordEncoder) {
        return new Staff(cf,name,surname,username,passwordEncoder.encode(password),type,telephone,email);
    }

}
