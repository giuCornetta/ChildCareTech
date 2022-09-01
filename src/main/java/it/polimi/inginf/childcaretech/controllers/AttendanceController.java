package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.Child;
import it.polimi.inginf.childcaretech.data.childAttendance.ChildAttendance;
import it.polimi.inginf.childcaretech.repositories.ChildAttendanceRepository;
import it.polimi.inginf.childcaretech.repositories.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@RequestMapping(value = "/attendance", produces = "application/json")
public class AttendanceController {

    private final ChildAttendanceRepository childAttendanceRepository;
    private final ChildRepository childRepository;
    @Autowired
    public AttendanceController(ChildAttendanceRepository repo1, ChildRepository repo2){
        this.childAttendanceRepository = repo1;
        this.childRepository = repo2;
    }

    @GetMapping
    public ModelAndView welcome(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("attendance.html");
        return modelAndView;
    }

    @GetMapping("/children/{date}")
    public Iterable<ChildAttendance> findAttendancesByData(@PathVariable("date") String date_string) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(date_string, formatter);
        //Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        List<ChildAttendance> attendances = childAttendanceRepository.findByPrimarykeyDate(date);
        System.out.println("children present: " + attendances);
        List<Child> children = childRepository.findChildByIdIsNotInChildAttendance(date);
        System.out.println("children absent: " + children);
        for(Child c : children){
            if(c != null){
                attendances.add(new ChildAttendance(c, LocalDate.parse(date_string), null, null));
            }
        }
        List<ChildAttendance> finalAttendances = new ArrayList<>();
        for(ChildAttendance ca : attendances){
            if(ca != null) {
                finalAttendances.add(ca); //Altrimenti modificava la stessa collection
            }
        }
        return finalAttendances;
        //return childAttendanceRepository.findChildAttendancesOuterJoin(timestamp);
    }
}
