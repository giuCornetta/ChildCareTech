package it.polimi.inginf.childcaretech.controllers;

import it.polimi.inginf.childcaretech.data.Child;
import it.polimi.inginf.childcaretech.data.childAttendance.ScannedChildAttendance;
import it.polimi.inginf.childcaretech.data.childAttendance.ChildAttendance;
import it.polimi.inginf.childcaretech.data.trip.tripAttendance.ScannedChildTrip;
import it.polimi.inginf.childcaretech.data.trip.tripAttendance.TripAttendance;
import it.polimi.inginf.childcaretech.data.trip.tripRegistration.TripRegistration;
import it.polimi.inginf.childcaretech.repositories.ChildAttendanceRepository;
import it.polimi.inginf.childcaretech.repositories.ChildRepository;
import it.polimi.inginf.childcaretech.repositories.tripRepositories.TripAttendanceRepository;
import it.polimi.inginf.childcaretech.repositories.tripRepositories.TripRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(produces="application/json") //Handles requests for "/anagrafica", ma dato che ho specificato json, un altro controller può occuparsi di /anagrafica
@CrossOrigin(origins="*")
public class MobileController {
    ChildAttendanceRepository childAttendanceRepository;
    TripAttendanceRepository tripAttendanceRepository;
    ChildRepository childRepository;
    TripRegistrationRepository tripRegistrationRepository;

    @Autowired
    public MobileController(ChildAttendanceRepository childAttendanceRepository, TripAttendanceRepository tripAttendanceRepository, ChildRepository childRepository, TripRegistrationRepository tripRegistrationRepository){
        this.childAttendanceRepository = childAttendanceRepository;
        this.tripAttendanceRepository = tripAttendanceRepository;
        this.childRepository = childRepository;
        this.tripRegistrationRepository = tripRegistrationRepository;
    }

    @PostMapping(value = "/attendance/register", consumes = "application/json")
    public ResponseEntity<?> registerChildAttendance(@RequestBody ScannedChildAttendance scannedChildAttendance){
        Optional<Child> child = childRepository.findByIdAndCfAndNameAndSurname(scannedChildAttendance.getId(), scannedChildAttendance.getCf(), scannedChildAttendance.getName(), scannedChildAttendance.getSurname());
        if(child.isPresent()){
            Optional<ChildAttendance> childAttendance = childAttendanceRepository.findChildAttendanceByPrimarykeyIdChildAndPrimarykeyDate(child.get().getId(), LocalDate.now());
            if(childAttendance.isPresent()){
                if(childAttendance.get().getEntranceTime() == null){ //FIXME this check should be useless
                    ChildAttendance attendance = new ChildAttendance(child.get(), LocalDate.now(), scannedChildAttendance.getTime());
                    return new ResponseEntity<>(childAttendanceRepository.save(attendance), HttpStatus.CREATED);
                } else {
                    if(childAttendance.get().getExitTime() == null) {
                        ChildAttendance attendance = childAttendance.get();
                        attendance.setExitTime(scannedChildAttendance.getTime());
                        return new ResponseEntity<>(childAttendanceRepository.save(attendance), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>("The child has already been signed out", HttpStatus.BAD_REQUEST);
                    }
                }
            } else {
                ChildAttendance attendance = new ChildAttendance(child.get(), LocalDate.now(), scannedChildAttendance.getTime());
                return new ResponseEntity<>(childAttendanceRepository.save(attendance), HttpStatus.CREATED);
            }
        }
          return new ResponseEntity<>("The child could not be found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/trips/registerAttendance", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> registerTripStopAttendance(@RequestBody ScannedChildTrip scannedChild){
        Optional<Child> child = childRepository.findByIdAndCfAndNameAndSurname(scannedChild.getId(), scannedChild.getCf(), scannedChild.getName(), scannedChild.getSurname());
        if(child.isPresent()){
            Optional<TripAttendance> tripAttendance = tripAttendanceRepository.findByPrimarykeyIdChildAndPrimarykeyIdTripStop(child.get().getId(), scannedChild.getIdTripStop());
            if(tripAttendance.isEmpty()){
                Optional<TripRegistration> tripRegistration = tripRegistrationRepository.findByPrimarykeyIdChildAndPrimarykeyIdTrip(scannedChild.getId(), scannedChild.getIdTrip());
                if(tripRegistration.isPresent()){
                    System.out.println(tripRegistration);
                    if(scannedChild.getBus().equals(tripRegistration.get().getBus())){
                        TripAttendance attendance = new TripAttendance(scannedChild.getIdTripStop(), scannedChild.getId());
                        return new ResponseEntity<>(tripAttendanceRepository.save(attendance), HttpStatus.CREATED);
                    } else {
                        return new ResponseEntity<>("The child(" + child.get().getName() + " " + child.get().getSurname() +") should not be on this bus but on " + tripRegistration.get().getBus(), HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("The child(" + child.get().getName() + " " + child.get().getSurname() +") is not registered for this trip", HttpStatus.BAD_REQUEST);
                }
            } else {
                Optional<TripRegistration> tripRegistration = tripRegistrationRepository.findByPrimarykeyIdChildAndPrimarykeyIdTrip(scannedChild.getId(), scannedChild.getIdTrip());
                return new ResponseEntity<>("The child (" + child.get().getName() + " " + child.get().getSurname() +") is already on bus: " + tripRegistration.get().getBus(), HttpStatus.BAD_REQUEST); //Does not create trip attendance because it already exists
            }
        }
        return new ResponseEntity<>("The child does not exist", HttpStatus.BAD_REQUEST);
    }


    @GetMapping("/trips/attendance/{idStop}/{bus}")
    public List<Child> getPresentChildren(@PathVariable("idStop") int idStop, @PathVariable("bus") String bus){
        return childRepository.findChildrenOnTheBusTripStop(idStop, bus);
    }

    @GetMapping("/trips/attendance/missing/{idStop}/{bus}")
    public List<Child> getMissingChildren(@PathVariable("idStop") int idStop, @PathVariable("bus") String bus){
        return childRepository.findChildrenNotOnBusTripStop(idStop, bus);
    }

}
