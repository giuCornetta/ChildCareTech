package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.Child;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChildRepository extends CrudRepository<Child, Integer> { //Entity type, Id type

    @Query(value = "Select * from Child where id not in (Select ID_Child from ChildAttendance where date = :date)", nativeQuery = true)
    List<Child> findChildByIdIsNotInChildAttendance(@Param("date") LocalDate date);

    @Query("Select doctor.id from Child where id= :id")
    int findDoctorById(@Param("id") int id);

    @Query(value = "Select * from CHILD where ID not in (Select ID_Child from CHILDALLERGEN join DISHALLERGEN on Childallergen.ID_Allergen = Dishallergen.ID_allergen where ID_DISH = :dishId ) and ID not in (Select ID_Child from CAFETERIAORDER where date = :date and ID_Dish = :dishId)", nativeQuery = true)
    List<Child> findChildNotAllergicTo(@Param("dishId") int dishId, @Param("date") LocalDate date);

    @Query(value="Select * from Child where ID not in (Select ID_Child from CafeteriaOrder where date = :date)", nativeQuery = true)
    List<Child> findChildMissingOrder(@Param("date") LocalDate date);

    Optional<Child> findByIdAndCfAndNameAndSurname(int id, String cf, String name, String surname);

    @Query(value = "Select * from Child join Tripattendance on ID = ID_Child join TRIPREGISTRATION on ID = tripregistration.ID_CHIld and ID_Trip_Stop = :idStop and Bus= :bus", nativeQuery = true)
    List<Child> findChildrenOnTheBusTripStop(@Param("idStop") int idStop, @Param("bus") String licensePlate);

    @Query(value = "Select * from Child join Tripregistration on ID = ID_Child where bus = :bus and ID not in (Select ID_Child from Tripattendance where id_trip_stop = :idStop)", nativeQuery = true)
    List<Child> findChildrenNotOnBusTripStop(@Param("idStop") int idStop, @Param("bus") String licensePlate);


}
