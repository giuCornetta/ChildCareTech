package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.ChildAttendance;
import it.polimi.inginf.childcaretech.data.ChildAttendancePK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface ChildAttendanceRepository extends CrudRepository<ChildAttendance, ChildAttendancePK> {

    public final static String ATTENDANCE = "Select ID_Child, Date, EntranceTime, ExitTime from Child left outer join (select * from CHILDATTENDANCE where date = :date) as c on CHILD.id = c.id_child";




    @Query(value = "Select ID_Child, Date, EntranceTime, ExitTime from Child left outer join (select * from CHILDATTENDANCE where date = :date) as c on CHILD.id = c.id_child", nativeQuery = true)
    List<ChildAttendance> findChildAttendancesOuterJoin(@Param("date") Date date);

    @Query(value = "Select ID_Child, Date, EntranceTime, ExitTime from Child left outer join (select * from CHILDATTENDANCE where date = current_date) as c on CHILD.id = c.id_child", nativeQuery = true)
    List<ChildAttendance> findChildAttendancesTodayOuterJoin();




}
