package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.Child;
import it.polimi.inginf.childcaretech.data.CreatedChild;
import it.polimi.inginf.childcaretech.data.Doctor;
import it.polimi.inginf.childcaretech.data.Parent;
import it.polimi.inginf.childcaretech.data.formData.FormSelection;
import it.polimi.inginf.childcaretech.repositories.ChildRepository;
import it.polimi.inginf.childcaretech.repositories.DoctorRepository;
import it.polimi.inginf.childcaretech.repositories.ParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(path = "/createChild", produces = "application/json")
@CrossOrigin(origins="*")
public class CreateChildController {
    private final ParentRepository parentRepository;
    private final DoctorRepository doctorRepository;
    private final ChildRepository childRepository;

    @Autowired
    public CreateChildController(ParentRepository parentRepository, DoctorRepository doctorRepository, ChildRepository childRepository){
        this.parentRepository = parentRepository;
        this.doctorRepository = doctorRepository;
        this.childRepository = childRepository;
    }

    @GetMapping
    public ModelAndView welcome(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("createChild.html");
        return modelAndView;
    }

    @GetMapping("/parents")
    public List<FormSelection> getParentsSelection(){
        List<Parent> parents = (List<Parent>) parentRepository.findAll();
        return parents.stream().map((parent) -> new FormSelection(parent.getId(), parent.getName(), parent.getSurname(), parent.getCf())).toList();
    }

    @GetMapping("/doctors")
    public List<FormSelection> getDoctorsSelection(){
        List<Doctor> doctors = (List<Doctor>) doctorRepository.findAll();
        return doctors.stream().map((doctor) -> new FormSelection(doctor.getId(), doctor.getName(), doctor.getSurname(), doctor.getCf())).toList();
    }

    @PostMapping(path = "/createDoctor", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor createNewDoctor(@RequestBody Doctor doctor){
        return doctorRepository.save(doctor);
        //return doctorRepository.findById(4).orElse(null); //DEBUG

    }

    @PostMapping(path = "/createParent", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Parent createNewParent(@RequestBody Parent parent){
        return parentRepository.save(parent);
        //return parentRepository.findById(6).orElse(null); //DEBUG
    }

    @PostMapping(path = "/create", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Child createNewChild(@RequestBody CreatedChild c){
        Parent parent1 = parentRepository.findById(c.getParent1()).orElse(null);
        Parent parent2 = parentRepository.findById(c.getParent2()).orElse(null);
        Doctor doctor = doctorRepository.findById(c.getDoctor()).orElse(null);

        Child child = new Child(c.getId(), c.getCf(), c.getName(), c.getSurname(), c.getDob(), c.getAddress(), parent1, parent2, doctor);

        return childRepository.save(child); //FIXME check it works
    }

}
