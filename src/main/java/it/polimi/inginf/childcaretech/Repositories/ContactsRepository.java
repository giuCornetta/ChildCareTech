package it.polimi.inginf.childcaretech.Repositories;

import it.polimi.inginf.childcaretech.Data.Contatto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactsRepository extends CrudRepository<Contatto, Integer> {
    List<Contatto> findByCodBambino(int codiceBambino);
}
