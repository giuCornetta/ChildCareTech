package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.Staff;
import org.springframework.data.repository.CrudRepository;

public interface StaffRepository extends CrudRepository<Staff, Integer> {
    /**
     * Looks up a Staff member by their username
     * @param username Username string of staff member
     * @return Staff object that matches with the provided username
     */
    Staff findByUsername(String username); //Automatically generated the implementation.

}
