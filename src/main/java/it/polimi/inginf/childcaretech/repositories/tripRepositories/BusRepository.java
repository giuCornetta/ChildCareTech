package it.polimi.inginf.childcaretech.repositories.tripRepositories;

import it.polimi.inginf.childcaretech.data.trip.bus.Bus;
import it.polimi.inginf.childcaretech.data.trip.bus.BusPK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BusRepository extends CrudRepository<Bus, BusPK> {

    List<Bus> findByPrimarykeyIdTrip(int tripId);

}
