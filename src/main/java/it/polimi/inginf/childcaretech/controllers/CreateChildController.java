package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.Doctor;
import it.polimi.inginf.childcaretech.data.Parent;
import it.polimi.inginf.childcaretech.data.formData.FormSelection;
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

    private ParentRepository parentRepository;
    private DoctorRepository doctorRepository;

    @Autowired
    public CreateChildController(ParentRepository parentRepository, DoctorRepository doctorRepository){
        this.parentRepository = parentRepository;
        this.doctorRepository = doctorRepository;
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
        List<FormSelection> parentSelection = parents.stream().map((parent) -> { return new FormSelection(parent.getId(), parent.getName(), parent.getSurname(), parent.getCf());}).toList();
        return parentSelection;
    }

    @GetMapping("/doctors")
    public List<FormSelection> getDosctorsSelection(){
        List<Doctor> doctors = (List<Doctor>) doctorRepository.findAll();
        List<FormSelection> doctorSelection = doctors.stream().map((doctor) -> { return new FormSelection(doctor.getId(), doctor.getName(), doctor.getSurname(), doctor.getCf());}).toList();
        return doctorSelection;
    }

    @PostMapping(path = "/createDoctor", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Doctor createNewDoctor(@RequestBody Doctor doctor){
        System.out.println("Doctor arrived to BACKEND");
        return doctorRepository.save(doctor); //FIXME check it works

    }

}
