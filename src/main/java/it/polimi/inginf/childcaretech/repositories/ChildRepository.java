package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.Child;
import org.springframework.data.repository.CrudRepository;

public interface ChildRepository extends CrudRepository<Child, Integer> { //Entity type, Id type
}
