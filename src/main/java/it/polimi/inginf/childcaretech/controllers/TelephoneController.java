package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.telephoneNumbers.TelephoneNumber;
import it.polimi.inginf.childcaretech.repositories.TelephoneNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(produces = "application/json")
public class TelephoneController {

    TelephoneNumberRepository telephoneNumberRepository;

    @Autowired
    public TelephoneController(TelephoneNumberRepository repo){
        this.telephoneNumberRepository = repo;
    }

    @GetMapping("/telephoneNumbers/{parentId}")
    public List<TelephoneNumber> getTelephoneNumbers(@PathVariable("parentId") int parentId){
        return telephoneNumberRepository.findByPrimarykeyIdParent(parentId);
        //return new ArrayList<>(telephoneNumbers.stream().map(x -> new TelephoneNumberHelper(x.getPrimarykey().getIdParent(), x.getPrimarykey().getTelephone(), x.getDescription())).toList());
    }

    @PostMapping(path = "/telephone", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TelephoneNumber addTelephoneNumbers(@RequestBody TelephoneNumber telephoneNumber){
        return telephoneNumberRepository.save(telephoneNumber);
    }

}
