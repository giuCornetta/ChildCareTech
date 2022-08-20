package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.ChildAttendance;
import it.polimi.inginf.childcaretech.repositories.ChildAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/attendance", produces = "application/json")
public class AttendanceController {

    private ChildAttendanceRepository childAttendanceRepository;
    @Autowired
    public AttendanceController(ChildAttendanceRepository repo){
        this.childAttendanceRepository = repo;
    }

    @GetMapping
    public ModelAndView welcome(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("attendance.html");
        return modelAndView;
    }

    @GetMapping("/children")
    public Iterable<ChildAttendance> findAttendances(){
        return childAttendanceRepository.findChildAttendancesTodayOuterJoin();
    }
}
