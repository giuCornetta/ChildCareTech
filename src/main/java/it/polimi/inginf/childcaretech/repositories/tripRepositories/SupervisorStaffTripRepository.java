package it.polimi.inginf.childcaretech.repositories.tripRepositories;

import it.polimi.inginf.childcaretech.data.trip.supervisorStaffTrip.SupervisorStaffTrip;
import it.polimi.inginf.childcaretech.data.trip.supervisorStaffTrip.SupervisorStaffTripPK;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SupervisorStaffTripRepository extends CrudRepository<SupervisorStaffTrip, SupervisorStaffTripPK> {

    List<SupervisorStaffTrip> findByBus(String bus);
}
