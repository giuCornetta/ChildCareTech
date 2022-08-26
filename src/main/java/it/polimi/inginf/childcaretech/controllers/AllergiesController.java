package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.Allergen;
import it.polimi.inginf.childcaretech.data.childAllergen.ChildAllergen;
import it.polimi.inginf.childcaretech.data.formData.FormSelection;
import it.polimi.inginf.childcaretech.repositories.AllergenRepository;
import it.polimi.inginf.childcaretech.repositories.AllergyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(produces="application/json")
public class AllergiesController {

    private AllergyRepository allergyRepository;
    private AllergenRepository allergenRepository;

    @Autowired
    public AllergiesController(AllergyRepository allergyRepository, AllergenRepository allergenRepository){
        this.allergyRepository = allergyRepository;
        this.allergenRepository = allergenRepository;
    }

    @GetMapping("/allergies/{childId}")
    public List<Allergen> getAllergies(@PathVariable("childId") int childId){
        System.out.println("Entered in method");
        List<ChildAllergen> childAllergens = allergyRepository.findByPrimarykeyIdChild(childId);
        System.out.println("childAllergens: " + childAllergens);
        List<Allergen> allergensNames = new ArrayList<>();
        for(ChildAllergen allergen : childAllergens){
            allergensNames.add(allergenRepository.findById(allergen.getPrimarykey().getIdAllergen()).orElse(null));
        }
        return allergensNames;
    }

    @PostMapping(path = "/allergies", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ChildAllergen createNewDoctor(@RequestBody ChildAllergen childAllergen){
        System.out.println("allergene dalla post: " + childAllergen);
        return allergyRepository.save(childAllergen);
        //return doctorRepository.findById(4).orElse(null); //DEBUG

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
