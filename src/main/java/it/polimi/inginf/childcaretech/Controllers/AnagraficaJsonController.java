package it.polimi.inginf.childcaretech.Controllers;

import it.polimi.inginf.childcaretech.Data.Child;
import it.polimi.inginf.childcaretech.Data.Contact;
import it.polimi.inginf.childcaretech.Repositories.ChildRepository;
import it.polimi.inginf.childcaretech.Repositories.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/anagrafica", produces="application/json") //Handles requests for "/anagrafica", ma dato che ho specificato json, un altro controller può occuparsi di /anagrafica
@CrossOrigin(origins="*")
public class AnagraficaJsonController {

    private final ChildRepository childRepository;
    private final ContactsRepository contactRepo;

    @Autowired
    public AnagraficaJsonController(ChildRepository repo1, ContactsRepository repo2){
        this.childRepository = repo1;
        this.contactRepo = repo2;
    }

    @GetMapping("/children")
    public Iterable<Child> children(){
        return  childRepository.findAll();
    }

    @GetMapping("/contacts/{childId}")
    public Iterable<Contact> contacts(@PathVariable("childId") int childId){
        return contactRepo.findByIdChild(childId);
    }
}
