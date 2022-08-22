package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.Child;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ChildRepository extends CrudRepository<Child, Integer> { //Entity type, Id type

    @Query(value = "Select * from Child where id not in (Select ID_Child from ChildAttendance where date = :date)", nativeQuery = true)
    List<Child> findChildByIdIsNotInChildAttendance(@Param("date") Timestamp date);
}
