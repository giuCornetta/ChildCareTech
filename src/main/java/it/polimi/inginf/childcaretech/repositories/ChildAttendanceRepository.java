package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.childAttendance.ChildAttendance;
import it.polimi.inginf.childcaretech.data.childAttendance.ChildAttendancePK;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChildAttendanceRepository extends CrudRepository<ChildAttendance, ChildAttendancePK> {

    //@Query(value = "Select ID_Child, Name, Surname, Date, EntranceTime, ExitTime from Child left outer join (select * from CHILDATTENDANCE where date = :date) as attendance on child.ID = attendance.ID_Child", nativeQuery = true)
    //List<ChildAttendance> findChildAttendancesOuterJoin(@Param("date") Timestamp date);

    List<ChildAttendance> findByPrimarykeyDate(LocalDate date);


    Optional<ChildAttendance> findChildAttendanceByPrimarykeyIdChildAndPrimarykeyDate(int idChild, LocalDate date);
    //List<ChildAttendance> findChildAttendanceByDate(LocalDate date);
}
