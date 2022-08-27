package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.CreatedChild;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreatedChildRepository extends CrudRepository<CreatedChild, Integer> {

    @Query(value = "Select * from Child where ID not in (Select ID_Child from tripregistration where ID_Trip = :tripId)", nativeQuery = true)
    List<CreatedChild> findByIdNotInTripRegistration(@Param("tripId") int tripId);

}
