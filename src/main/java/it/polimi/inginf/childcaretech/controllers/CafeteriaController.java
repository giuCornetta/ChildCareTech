package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.Child;
import it.polimi.inginf.childcaretech.data.cafeteria.CafeteriaMenu;
import it.polimi.inginf.childcaretech.data.cafeteria.cafeteriaOrder.CafeteriaOrder;
import it.polimi.inginf.childcaretech.data.cafeteria.cafeteriaOrder.CafeteriaOrderJoined;
import it.polimi.inginf.childcaretech.data.formData.FormSelection;
import it.polimi.inginf.childcaretech.repositories.*;
import it.polimi.inginf.childcaretech.repositories.cafeteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value = "/cafeteria", produces = "application/json")
public class CafeteriaController {

    CafeteriaMenuRepository cafeteriaMenuRepository;
    CafeteriaOrderRepository cafeteriaOrderRepository;
    CafeteriaOrderJoinedRepository cafeteriaOrderJoinedRepository;
    DishRepository dishRepository;
    DishAllergenRepository dishAllergenRepository;
    ChildRepository childRepository;


    @Autowired
    public CafeteriaController(CafeteriaMenuRepository repo1, CafeteriaOrderRepository repo2, DishRepository repo3, DishAllergenRepository repo4, ChildRepository repo5, CafeteriaOrderJoinedRepository repo6){
        this.cafeteriaMenuRepository = repo1;
        this.cafeteriaOrderRepository = repo2;
        this.dishRepository = repo3;
        this.dishAllergenRepository = repo4;
        this.childRepository = repo5;
        this.cafeteriaOrderJoinedRepository = repo6;
    }

    @GetMapping
    public ModelAndView welcome(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cafeteria.html");
        return modelAndView;
    }

    @GetMapping("/menu/{date}")
    public CafeteriaMenu getTodaysMenu(@PathVariable("date") String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        List<CafeteriaMenu> menus = cafeteriaMenuRepository.findByDate(date);
        CafeteriaMenu menu = null;
        if(!menus.isEmpty())
            return menus.get(0);
        else
            return menu;
    }

    //@GetMapping("/menu/{date}) //restituisce anche quelli di oggi

    @GetMapping("/order/{dishId}/{date}")
    public List<FormSelection> getTodaysMenu(@PathVariable("dishId") int dishId, @PathVariable("date") String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        List<Child> children = childRepository.findChildNotAllergicTo(dishId, date);
        return children.stream().map((child) -> new FormSelection(child.getId(), child.getName(), child.getSurname(), child.getCf())).toList();
    }

    @GetMapping("/history/orders/{date}")
    public List<CafeteriaOrderJoined> getOrdersFrom(@PathVariable("date") String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return cafeteriaOrderJoinedRepository.findByPrimarykeyDateOrderByPrimarykeyIdChild(date);
    }

    @GetMapping("/history/orders/missing/{date}")
    public List<Child> getMissingOrdersFrom(@PathVariable("date") String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateString, formatter);
        return childRepository.findChildMissingOrder(date);
    }



    @PostMapping(path = "/order", consumes = "application/json")
    public CafeteriaOrder getTodaysMenu(@RequestBody CafeteriaOrder order){
        System.out.println("Order received: " + order);
        return cafeteriaOrderRepository.save(order);
    }

    //@GetMapping("/orders")

}
