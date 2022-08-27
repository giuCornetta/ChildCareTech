package it.polimi.inginf.childcaretech.repositories;

import it.polimi.inginf.childcaretech.data.cafeteria.Dish;
import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<Dish, Integer> {
}
