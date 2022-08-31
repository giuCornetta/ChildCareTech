package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.Child;
import it.polimi.inginf.childcaretech.data.Contact;
import it.polimi.inginf.childcaretech.repositories.ChildRepository;
import it.polimi.inginf.childcaretech.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(path="/details", produces="application/json") //Handles requests for "/anagrafica", ma dato che ho specificato json, un altro controller può occuparsi di /anagrafica
@CrossOrigin(origins="*")
public class DetailsController {

    private final ChildRepository childRepository;
    private final ContactRepository contactRepo;

    @Autowired
    public DetailsController(ChildRepository repo1, ContactRepository repo2){
        this.childRepository = repo1;
        this.contactRepo = repo2;
    }

    @GetMapping
    public ModelAndView welcome(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("details.html");
        return modelAndView;
    }

    @GetMapping("/children")
    public Iterable<Child> children(){
        return  childRepository.findAll();
    }

    @GetMapping("/contacts/{childId}")
    public Iterable<Contact> contacts(@PathVariable("childId") int childId){
        return contactRepo.findByIdChild(childId);
    }

    @PostMapping(value = "/contacts/add", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Contact createNewContact(@RequestBody Contact contact){
        return contactRepo.save(contact);
    }
}
