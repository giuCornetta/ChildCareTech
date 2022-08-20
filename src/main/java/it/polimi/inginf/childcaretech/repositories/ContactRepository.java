package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepository extends CrudRepository<Contact, Integer> {
    List<Contact> findByIdChild(int codiceBambino);
}
