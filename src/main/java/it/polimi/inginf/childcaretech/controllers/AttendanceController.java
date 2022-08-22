package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.Child;
import it.polimi.inginf.childcaretech.data.ChildAttendance;
import it.polimi.inginf.childcaretech.repositories.ChildAttendanceRepository;
import it.polimi.inginf.childcaretech.repositories.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping(value = "/attendance", produces = "application/json")
public class AttendanceController {

    private ChildAttendanceRepository childAttendanceRepository;
    private ChildRepository childRepository;
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

    /*@GetMapping("/children")
    public Iterable<ChildAttendance> findAttendances(){
        return childAttendanceRepository.findChildAttendancesTodayOuterJoin();
    }*/

    @GetMapping("/children/{date}")
    public Iterable<ChildAttendance> findAttendancesByData(@PathVariable("date") String date_string) throws ParseException {
        System.out.println(date_string);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //Parsing the given String to Date object
        Date date = formatter.parse(date_string);
        Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        List<ChildAttendance> attendances = childAttendanceRepository.findChildAttendancesOuterJoin(timestamp);
        System.out.println(attendances);
        List<Child> children = childRepository.findChildByIdIsNotInChildAttendance(timestamp);
        System.out.println("Bambini:" + children);
        for(Child c : children){
            System.out.println("c " + c);
            if(c != null){
                System.out.println("c è stato aggiunto");
                attendances.add(new ChildAttendance(c, LocalDate.parse(date_string), null, null));
            }
        }
        System.out.println("Lista completa: " + attendances);
        List<ChildAttendance> finalAttendances = new ArrayList<>();
        for(ChildAttendance ca : attendances){
            System.out.println("ca " + ca);
            if(ca != null) {
                finalAttendances.add(ca); //Altrimenti modificava la stessa collection
                System.out.println("ca è stato aggiunto");
            }
        }
        System.out.println(finalAttendances);
        return finalAttendances;
        //return childAttendanceRepository.findChildAttendancesOuterJoin(timestamp);
    }
}
