package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.Doctor;
import org.springframework.data.repository.CrudRepository;


public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
}
