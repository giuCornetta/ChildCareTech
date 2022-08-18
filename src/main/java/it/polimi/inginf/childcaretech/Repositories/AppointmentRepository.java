package it.polimi.inginf.childcaretech.Repositories;

import it.polimi.inginf.childcaretech.Data.Appointment;
import org.springframework.data.repository.CrudRepository;

public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {
}
