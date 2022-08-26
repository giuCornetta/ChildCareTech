package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.Allergen;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AllergenRepository extends CrudRepository<Allergen, Integer> {
    @Query(value = "Select * from allergen where id not in (select id_Allergen from childallergen where id_Child = :child )", nativeQuery = true)
    List<Allergen> findAllergensNotInChildAllergen(@Param("child") int child);

}
