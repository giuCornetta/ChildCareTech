package it.polimi.inginf.childcaretech.security;

import it.polimi.inginf.childcaretech.Repositories.StaffRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegistrationForm form) {
        staffRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }

}
