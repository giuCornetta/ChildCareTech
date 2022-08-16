package it.polimi.inginf.childcaretech.Repositories;

import it.polimi.inginf.childcaretech.Data.Bambino;
import org.springframework.data.repository.CrudRepository;

public interface ChildRepository extends CrudRepository<Bambino, Integer> { //Entity type, Id type
}
