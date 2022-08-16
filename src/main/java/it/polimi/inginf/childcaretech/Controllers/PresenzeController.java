package it.polimi.inginf.childcaretech.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/presenze")
public class PresenzeController {

    @GetMapping
    public String view(Model model){
        return "presenze";
    }
}
