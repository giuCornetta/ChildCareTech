package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.Child;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface ChildRepository extends CrudRepository<Child, Integer> { //Entity type, Id type

    @Query(value = "Select * from Child where id not in (Select ID_Child from ChildAttendance where date = :date)", nativeQuery = true)
    List<Child> findChildByIdIsNotInChildAttendance(@Param("date") Timestamp date);

    @Query("Select doctor.id from Child where id= :id")
    int findDoctorById(@Param("id") int id);

    @Query(value = "Select * from CHILD where ID not in (Select ID_Child from CHILDALLERGEN join DISHALLERGEN on Childallergen.ID_Allergen = Dishallergen.ID_allergen where ID_DISH = :dishId ) and ID not in (Select ID_Child from CAFETERIAORDER where date = :date and ID_Dish = :dishId)", nativeQuery = true)
    List<Child> findChildNotAllergicTo(@Param("dishId") int dishId, @Param("date") LocalDate date);

    @Query(value="Select * from Child where ID not in (Select ID_Child from CafeteriaOrder where date = :date)", nativeQuery = true)
    List<Child> findChildMissingOrder(@Param("date") LocalDate date);

}
