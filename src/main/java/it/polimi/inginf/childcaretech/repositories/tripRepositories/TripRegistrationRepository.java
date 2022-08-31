package it.polimi.inginf.childcaretech.repositories.tripRepositories;

import it.polimi.inginf.childcaretech.data.trip.tripRegistration.TripRegistration;
import it.polimi.inginf.childcaretech.data.trip.tripRegistration.TripRegistrationPK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TripRegistrationRepository extends CrudRepository<TripRegistration, TripRegistrationPK> {
    List<TripRegistration> findByBus(String bus);
    Optional<TripRegistration> findByPrimarykeyIdChildAndPrimarykeyIdTrip(int idChild, int idTrip);

}
