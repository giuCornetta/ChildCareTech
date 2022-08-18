package it.polimi.inginf.childcaretech.Controllers;

import it.polimi.inginf.childcaretech.Data.Appointment;
import it.polimi.inginf.childcaretech.Repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping(value = "/appointments", produces="application/json")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentController(AppointmentRepository appRepo){
        this.appointmentRepository = appRepo;
    }

    /*@GetMapping
    public String view(Model model){
        return "visite";
    }*/
    @GetMapping
    public ModelAndView welcome(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("appointments.html");
        return modelAndView;
    }

    @GetMapping("/booked")
    public Iterable<Appointment> getBookedAppointments(){
        return appointmentRepository.findAll();
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
