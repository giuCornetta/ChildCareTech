package it.polimi.inginf.childcaretech.repositories.tripRepositories;

import it.polimi.inginf.childcaretech.data.trip.supervisorStaffTrip.CreatedSupervisorStaffTrip;
import it.polimi.inginf.childcaretech.data.trip.supervisorStaffTrip.SupervisorStaffTripPK;
import org.springframework.data.repository.CrudRepository;


public interface CreatedSupervisorStaffTripRepository extends CrudRepository<CreatedSupervisorStaffTrip, SupervisorStaffTripPK> {
}
