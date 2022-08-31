package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.Allergen;
import it.polimi.inginf.childcaretech.data.childAllergen.ChildAllergen;
import it.polimi.inginf.childcaretech.data.formData.FormSelection;
import it.polimi.inginf.childcaretech.repositories.AllergenRepository;
import it.polimi.inginf.childcaretech.repositories.ChildAllergenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(produces="application/json")
public class AllergiesController {

    private ChildAllergenRepository childAllergenRepository;
    private AllergenRepository allergenRepository;

    @Autowired
    public AllergiesController(ChildAllergenRepository childAllergenRepository, AllergenRepository allergenRepository){
        this.childAllergenRepository = childAllergenRepository;
        this.allergenRepository = allergenRepository;
    }

    @GetMapping("/allergies/{childId}")
    public List<Allergen> getAllergies(@PathVariable("childId") int childId){
        List<ChildAllergen> childAllergens = childAllergenRepository.findByPrimarykeyIdChild(childId);
        List<Allergen> allergensNames = new ArrayList<>();
        for(ChildAllergen allergen : childAllergens){
            allergensNames.add(allergenRepository.findById(allergen.getPrimarykey().getIdAllergen()).orElse(null));
        }
        return allergensNames;
    }

    @PostMapping(path = "/allergies", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ChildAllergen createNewDoctor(@RequestBody ChildAllergen childAllergen){
        return childAllergenRepository.save(childAllergen);
    }

    @GetMapping("/allergens")
    public List<FormSelection> getAllAllergens(){
        return ( (List<Allergen>) allergenRepository.findAll()).stream().map((allergen) -> new FormSelection(allergen.getId(), allergen.getName())).toList();
    }

    @GetMapping("/allergens/{childId}")
    public List<FormSelection> getMissingAllergies(@PathVariable("childId") int childId){
        return allergenRepository.findAllergensNotInChildAllergen(childId)
                .stream().map((allergen) -> new FormSelection(allergen.getId(), allergen.getName())).toList();
    }
}
