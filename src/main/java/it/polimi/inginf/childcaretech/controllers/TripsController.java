package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.formData.BusFormSelection;
import it.polimi.inginf.childcaretech.data.formData.FormSelection;
import it.polimi.inginf.childcaretech.data.trip.Trip;
import it.polimi.inginf.childcaretech.data.trip.TripStop;
import it.polimi.inginf.childcaretech.data.trip.bus.Bus;
import it.polimi.inginf.childcaretech.data.trip.supervisorStaffTrip.SupervisorStaffTrip;
import it.polimi.inginf.childcaretech.data.trip.tripRegistration.CreatedTripRegistration;
import it.polimi.inginf.childcaretech.data.trip.tripRegistration.TripRegistration;
import it.polimi.inginf.childcaretech.repositories.CreatedChildRepository;
import it.polimi.inginf.childcaretech.repositories.tripRepositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/trips", produces="application/json")
public class TripsController {

    TripRepository tripRepository;
    TripStopRepository tripStopRepository;
    TripRegistrationRepository tripRegistrationRepository;
    CreatedChildRepository createdChildRepository;
    CreatedTripRegistrationRepository createdTripRegistrationRepository;
    BusRepository busRepository;
    SupervisorStaffTripRepository supervisorStaffTripRepository;

    @Autowired
    public TripsController(TripRepository tripRepository, TripStopRepository tripStopRepository, TripRegistrationRepository tripRegistrationRepository,
                           CreatedChildRepository createdChildRepository, CreatedTripRegistrationRepository createdTripRegistrationRepository, BusRepository busRepository,
                           SupervisorStaffTripRepository supervisorStaffTripRepository) {
        this.tripRepository = tripRepository;
        this.tripStopRepository = tripStopRepository;
        this.tripRegistrationRepository = tripRegistrationRepository;
        this.createdChildRepository = createdChildRepository;
        this.createdTripRegistrationRepository = createdTripRegistrationRepository;
        this.busRepository = busRepository;
        this.supervisorStaffTripRepository = supervisorStaffTripRepository;
    }

    @GetMapping
    public ModelAndView welcome(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("trips.html");
        return modelAndView;
    }

    @GetMapping("/list")
    public List<Trip> getTrips(){
        return tripRepository.findAllByOrderByDepartureDateDesc();
        //orderby date descending VOGLIO QUELLI PIù NUOVI PRIMA
    }

    @GetMapping("/stops/{tripId}")
    public List<TripStop> getTripStops(@PathVariable("tripId") int tripId){
        return tripStopRepository.findByIdTrip(tripId);
    }

    /*@GetMapping("/registration/{tripId}")
    public List<TripRegistration> getChildrenRegistered(@PathVariable("tripId") int tripId){
        return tripRegistrationRepository.findByPrimarykeyIdTrip(tripId);
    }*/

    @GetMapping("/registration/{tripId}/missing")
    public List<FormSelection> getChildrenNotRegistered(@PathVariable("tripId") int tripId){
        return createdChildRepository.findByIdNotInTripRegistration(tripId).stream().map((child) -> new FormSelection(child.getId(), child.getName(), child.getSurname(), child.getCf())).toList();
    }

    @GetMapping("/buses/{tripId}")
    public List<BusFormSelection> getBuses(@PathVariable("tripId") int tripId){
        return busRepository.findByPrimarykeyIdTrip(tripId).stream().map((bus) -> new BusFormSelection(bus.getPrimarykey().getLicensePlate())).toList();
    }

    @PostMapping(value = "/register", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedTripRegistration registerChildToTrip(@RequestBody CreatedTripRegistration tripRegistration){
        return createdTripRegistrationRepository.save(tripRegistration);
    }

    @GetMapping("/registration/children/{tripId}")
    public List<List<TripRegistration>> getRegisteredChildGroupedByBus(@PathVariable("tripId") int tripId){
        List<Bus> buses = busRepository.findByPrimarykeyIdTrip(tripId);
        List<List<TripRegistration>> listOfBusChild = new ArrayList<>();
        for(Bus bus : buses){
            listOfBusChild.add(tripRegistrationRepository.findByBus(bus.getPrimarykey().getLicensePlate()));
        }
        return listOfBusChild;
    }

    @GetMapping("/registration/staff/{tripId}")
    public List<List<SupervisorStaffTrip>> getRegisteredStaffGroupedByBus(@PathVariable("tripId") int tripId){
        List<Bus> buses = busRepository.findByPrimarykeyIdTrip(tripId);
        List<List<SupervisorStaffTrip>> listOfBusStaff = new ArrayList<>();
        for(Bus bus : buses){
            listOfBusStaff.add(supervisorStaffTripRepository.findByBus(bus.getPrimarykey().getLicensePlate()));
        }
        return listOfBusStaff;
    }

}
