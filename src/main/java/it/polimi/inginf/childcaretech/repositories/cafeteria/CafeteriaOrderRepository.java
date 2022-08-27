package it.polimi.inginf.childcaretech.repositories.cafeteria;

import it.polimi.inginf.childcaretech.data.cafeteria.cafeteriaOrder.CafeteriaOrder;
import it.polimi.inginf.childcaretech.data.cafeteria.cafeteriaOrder.CafeteriaOrderPK;
import org.springframework.data.repository.CrudRepository;

public interface CafeteriaOrderRepository extends CrudRepository<CafeteriaOrder, CafeteriaOrderPK> {

    //List<CafeteriaOrder> findByPrimarykeyDate(LocalDate date);
}
