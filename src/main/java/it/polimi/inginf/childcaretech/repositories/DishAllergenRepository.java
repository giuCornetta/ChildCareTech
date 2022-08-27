package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.cafeteria.dishAllergen.DishAllergen;
import it.polimi.inginf.childcaretech.data.cafeteria.dishAllergen.DishAllergenPK;
import org.springframework.data.repository.CrudRepository;

public interface DishAllergenRepository extends CrudRepository<DishAllergen, DishAllergenPK> {
}
