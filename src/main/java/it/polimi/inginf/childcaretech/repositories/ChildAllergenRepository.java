package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.childAllergen.ChildAllergen;
import it.polimi.inginf.childcaretech.data.childAllergen.ChildAllergenPK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChildAllergenRepository extends CrudRepository<ChildAllergen, ChildAllergenPK> {

    //@Query(value = "Select * from childallergen where id_child = :child ", nativeQuery = true)
    List<ChildAllergen> findByPrimarykeyIdChild(/*@Param("child")*/ int childId);
}
