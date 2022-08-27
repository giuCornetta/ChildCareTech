package it.polimi.inginf.childcaretech.repositories.tripRepositories;


import it.polimi.inginf.childcaretech.data.trip.tripRegistration.CreatedTripRegistration;
import it.polimi.inginf.childcaretech.data.trip.tripRegistration.TripRegistrationPK;
import org.springframework.data.repository.CrudRepository;

public interface CreatedTripRegistrationRepository extends CrudRepository<CreatedTripRegistration, TripRegistrationPK> {

}
