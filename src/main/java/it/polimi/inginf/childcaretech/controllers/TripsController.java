package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.formData.BusFormSelection;
import it.polimi.inginf.childcaretech.data.formData.FormSelection;
import it.polimi.inginf.childcaretech.data.trip.Trip;
import it.polimi.inginf.childcaretech.data.trip.TripStop;
import it.polimi.inginf.childcaretech.data.trip.bus.Bus;
import it.polimi.inginf.childcaretech.data.trip.supervisorStaffTrip.CreatedSupervisorStaffTrip;
import it.polimi.inginf.childcaretech.data.trip.supervisorStaffTrip.SupervisorStaffTrip;
import it.polimi.inginf.childcaretech.data.trip.tripRegistration.CreatedTripRegistration;
import it.polimi.inginf.childcaretech.data.trip.tripRegistration.TripRegistration;
import it.polimi.inginf.childcaretech.repositories.CreatedChildRepository;
import it.polimi.inginf.childcaretech.repositories.StaffRepository;
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
    CreatedSupervisorStaffTripRepository createdSupervisorStaffTripRepository;
    StaffRepository staffRepository;

    @Autowired
    public TripsController(TripRepository tripRepository, TripStopRepository tripStopRepository,
                           TripRegistrationRepository tripRegistrationRepository, CreatedChildRepository createdChildRepository,
                           CreatedTripRegistrationRepository createdTripRegistrationRepository, BusRepository busRepository,
                           SupervisorStaffTripRepository supervisorStaffTripRepository, CreatedSupervisorStaffTripRepository createdSupervisorStaffTripRepository, StaffRepository staffRepository) {
        this.tripRepository = tripRepository;
        this.tripStopRepository = tripStopRepository;
        this.tripRegistrationRepository = tripRegistrationRepository;
        this.createdChildRepository = createdChildRepository;
        this.createdTripRegistrationRepository = createdTripRegistrationRepository;
        this.busRepository = busRepository;
        this.supervisorStaffTripRepository = supervisorStaffTripRepository;
        this.createdSupervisorStaffTripRepository = createdSupervisorStaffTripRepository;
        this.staffRepository = staffRepository;
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

    @GetMapping("/registration/children/{tripId}/missing")
    public List<FormSelection> getChildrenNotRegistered(@PathVariable("tripId") int tripId){
        return createdChildRepository.findByIdNotInTripRegistration(tripId).stream().map((child) -> new FormSelection(child.getId(), child.getName(), child.getSurname(), child.getCf())).toList();
    }

    @GetMapping("/registration/staff/{tripId}/missing")
    public List<FormSelection> getStaffNotRegistered(@PathVariable("tripId") int tripId){
        return staffRepository.findByNotInTrip(tripId).stream().map((staff) -> new FormSelection(staff.getId(), staff.getName(), staff.getSurname(), staff.getCf())).toList();
    }

    @GetMapping("/buses/{tripId}")
    public List<BusFormSelection> getBuses(@PathVariable("tripId") int tripId){
        return busRepository.findByPrimarykeyIdTrip(tripId).stream().map((bus) -> new BusFormSelection(bus.getPrimarykey().getLicensePlate())).toList();
    }

    @PostMapping(value = "/register/child", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedTripRegistration registerChildToTrip(@RequestBody CreatedTripRegistration tripRegistration){
        return createdTripRegistrationRepository.save(tripRegistration);
    }

    @GetMapping("/registration/children/{tripId}")
    public List<List<TripRegistration>> getRegisteredChildGroupedByBus(@PathVariable("tripId") int tripId){
        List<Bus> buses = busRepository.findByPrimarykeyIdTrip(tripId);
        List<List<TripRegistration>> listOfBusChild = new ArrayList<>();
        for(Bus bus : buses){
            listOfBusChild.add(tripRegistrationRepository.findByBusAndPrimarykeyIdTrip(bus.getPrimarykey().getLicensePlate(), tripId));
        }
        return listOfBusChild;
    }

    @GetMapping("/registration/staff/{tripId}")
    public List<List<SupervisorStaffTrip>> getRegisteredStaffGroupedByBus(@PathVariable("tripId") int tripId){
        List<Bus> buses = busRepository.findByPrimarykeyIdTrip(tripId);
        List<List<SupervisorStaffTrip>> listOfBusStaff = new ArrayList<>();
        for(Bus bus : buses){
            listOfBusStaff.add(supervisorStaffTripRepository.findByBusAndPrimarykeyIdTrip(bus.getPrimarykey().getLicensePlate(), tripId));
        }
        return listOfBusStaff;
    }

    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Trip createNewTrip(@RequestBody Trip trip){
        return tripRepository.save(trip);
    }


    @PostMapping(value = "/buses/add", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Bus registerBusToTrip(@RequestBody Bus bus){
        return busRepository.save(bus);
    }

    @PostMapping(value = "/register/staff", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatedSupervisorStaffTrip registerChildToTrip(@RequestBody CreatedSupervisorStaffTrip tripRegistration){
        return createdSupervisorStaffTripRepository.save(tripRegistration);
    }

    @PostMapping(value = "/stops/add", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TripStop registerChildToTrip(@RequestBody TripStop tripStop){
        return tripStopRepository.save(tripStop);
    }

}
