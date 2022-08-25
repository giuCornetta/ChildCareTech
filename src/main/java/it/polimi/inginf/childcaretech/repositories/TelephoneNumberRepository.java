package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.telephoneNumbers.TelephoneNumber;
import it.polimi.inginf.childcaretech.data.telephoneNumbers.TelephoneNumberPK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TelephoneNumberRepository extends CrudRepository<TelephoneNumber, TelephoneNumberPK> {
    List<TelephoneNumber> findByPrimarykeyIdParent(int idParent);
}
