package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.childAttendance.ChildAttendance;
import it.polimi.inginf.childcaretech.data.childAttendance.ChildAttendancePK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ChildAttendanceRepository extends CrudRepository<ChildAttendance, ChildAttendancePK> {

    @Query(value = "Select ID_Child, Name, Surname, Date, EntranceTime, ExitTime from Child left outer join (select * from CHILDATTENDANCE where date = :date) as attendance on child.ID = attendance.ID_Child", nativeQuery = true)
    List<ChildAttendance> findChildAttendancesOuterJoin(@Param("date") Timestamp date);

    //List<ChildAttendance> findChildAttendanceByDate(LocalDate date);
}
