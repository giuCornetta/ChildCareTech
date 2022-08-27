package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.cafeteria.cafeteriaOrder.CafeteriaOrderJoined;
import it.polimi.inginf.childcaretech.data.cafeteria.cafeteriaOrder.CafeteriaOrderPK;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CafeteriaOrderJoinedRepository extends CrudRepository<CafeteriaOrderJoined, CafeteriaOrderPK> {

    List<CafeteriaOrderJoined> findByPrimarykeyDateOrderByPrimarykeyIdChild(LocalDate date);
}
