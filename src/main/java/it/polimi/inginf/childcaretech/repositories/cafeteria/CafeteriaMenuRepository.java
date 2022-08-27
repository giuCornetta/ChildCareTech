package it.polimi.inginf.childcaretech.repositories.cafeteria;

import it.polimi.inginf.childcaretech.data.cafeteria.CafeteriaMenu;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CafeteriaMenuRepository extends CrudRepository<CafeteriaMenu, Integer> {
    List<CafeteriaMenu> findByDate(LocalDate date);
}
