package it.polimi.inginf.childcaretech.data.trip.tripAttendance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@ToString
@Getter
public class TripAttendancePK implements Serializable {
    @NotNull
    private int idTripStop;
    @NotNull
    private int idChild;
}
