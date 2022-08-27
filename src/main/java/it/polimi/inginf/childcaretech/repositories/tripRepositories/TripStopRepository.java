package it.polimi.inginf.childcaretech.repositories.tripRepositories;

import it.polimi.inginf.childcaretech.data.trip.TripStop;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TripStopRepository extends CrudRepository<TripStop, Integer> {
    List<TripStop> findByIdTrip(int idTrip);
}
