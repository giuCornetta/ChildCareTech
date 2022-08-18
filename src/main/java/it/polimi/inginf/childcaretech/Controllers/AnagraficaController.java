package it.polimi.inginf.childcaretech.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*@RestController
@RequestMapping(path="/anagrafica", produces="application/json") //Handles requests for "/anagrafica"
@CrossOrigin(origins="*")*/
@Controller
@RequestMapping("/details")
public class AnagraficaController {

    @GetMapping
    public String view(Model model){
        /*List<Children> children = new ArrayList<>((List<Children>) repository.findAll());

        model.addAttribute("kids", children);*/
        return "details";
    }
}
