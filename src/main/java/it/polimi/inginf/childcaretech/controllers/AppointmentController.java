package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.*;
import it.polimi.inginf.childcaretech.data.formData.FormSelection;
import it.polimi.inginf.childcaretech.data.telephoneNumbers.CreatedAppointment;
import it.polimi.inginf.childcaretech.repositories.AppointmentRepository;
import it.polimi.inginf.childcaretech.repositories.ChildRepository;
import it.polimi.inginf.childcaretech.repositories.DoctorRepository;
import it.polimi.inginf.childcaretech.repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@RequestMapping(value = "/appointments", produces="application/json")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;

    private final ChildRepository childRepository;

    private final StaffRepository staffRepository;

    private final DoctorRepository doctorRepository;

    @Autowired
    public AppointmentController(AppointmentRepository appRepo, ChildRepository childRepository, StaffRepository staffRepository, DoctorRepository doctorRepository){
        this.appointmentRepository = appRepo;
        this.childRepository = childRepository;
        this.staffRepository = staffRepository;
        this.doctorRepository = doctorRepository;
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

    @GetMapping("/children")
    public List<FormSelection> getChildrenSelection(){
        List<Child> children = (List<Child>) childRepository.findAll();
        return children.stream().map((child) -> new FormSelection(child.getId(), child.getName(), child.getSurname(), child.getCf())).toList();
    }

    @GetMapping("/staff")
    public List<FormSelection> getStaffSelection(){
        List<Staff> staffers = (List<Staff>) staffRepository.findAll();
        return staffers.stream().map((staff) -> new FormSelection(staff.getId(), staff.getName(), staff.getSurname(), staff.getCf())).toList();
    }

    @GetMapping(path="/doctor/{childId}")
    public Doctor getDoctor(@PathVariable("childId") int childId){
        int doctorId = childRepository.findDoctorById(childId);
        return doctorRepository.findById(doctorId).orElse(null);
    }

    @PostMapping(path = "/book", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Appointment bookNewAppointment(@RequestBody CreatedAppointment createdAppointment){
        Child child = childRepository.findById(createdAppointment.getIdChild()).orElse(null);
        Doctor doctor = doctorRepository.findById(createdAppointment.getIdDoctor()).orElse(null);
        Staff staff = staffRepository.findById(createdAppointment.getIdStaff()).orElse(null);

        Appointment appointment = new Appointment(child, doctor, staff, createdAppointment.getDate(), createdAppointment.getTime(), createdAppointment.getDescription());
        return appointmentRepository.save(appointment);
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
