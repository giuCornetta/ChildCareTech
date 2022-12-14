package it.polimi.inginf.childcaretech.security;

import it.polimi.inginf.childcaretech.repositories.StaffRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private StaffRepository staffRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController(StaffRepository staffRepo, PasswordEncoder passwordEncoder) {
        this.staffRepo = staffRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm(RegistrationForm form) {
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "registration";
        }
        else{
            staffRepo.save(form.toUser(passwordEncoder));
            return "redirect:/login";
        }
    }

}
