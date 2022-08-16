package it.polimi.inginf.childcaretech.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/visite")
public class VisiteController {

    @GetMapping
    public String view(Model model){
        return "visite";
    }

    /*@GetMapping("/visite/prenota/{idDottore}/{idBambino}", produces = "application/json")
    public @ResponseBody Iterable<Contatto> contatti(@PathVariable("idDoc") int idDoc, @PathVariable("idBam") int idBamb){
        //TODO aggiungere id staff che prenota visita
        //FIXME mettendo @ResponseBody non devo mettere rest controller e quindi forse posso fare sia template che json
        Alternativamente potrei tenere il controller come rest e usare un oggetto ModelAndView
        @RestController
public class MyRestController {
    @RequestMapping("/")
    public ModelAndView welcome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login.html");
        return modelAndView;
    }
}
    }*/

}
