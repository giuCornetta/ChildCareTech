package it.polimi.inginf.childcaretech.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*@RestController
@RequestMapping(path="/", produces="application/json") //Handles requests for "/"
@CrossOrigin(origins="*")*/
@Controller
public class HomeController {

    @GetMapping("/") //if rest @GetMapping
    public String home() {
        return "home";
    }
}
