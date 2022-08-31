package it.polimi.inginf.childcaretech.data.trip.tripAttendance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScannedChildTrip implements Serializable {
    private int id;
    private String cf;
    private String name;
    private String surname;
    private int idTrip;
    private int idTripStop;
    private String bus;
}
