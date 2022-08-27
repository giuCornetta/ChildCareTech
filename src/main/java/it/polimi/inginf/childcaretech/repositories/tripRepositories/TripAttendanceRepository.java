package it.polimi.inginf.childcaretech.repositories.tripRepositories;

import it.polimi.inginf.childcaretech.data.trip.tripAttendance.TripAttendance;
import it.polimi.inginf.childcaretech.data.trip.tripAttendance.TripAttendancePK;
import org.springframework.data.repository.CrudRepository;

public interface TripAttendanceRepository extends CrudRepository<TripAttendance, TripAttendancePK> {
    //FIXME delete if not used
}
