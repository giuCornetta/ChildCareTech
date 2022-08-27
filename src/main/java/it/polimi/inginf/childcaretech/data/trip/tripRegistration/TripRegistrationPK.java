package it.polimi.inginf.childcaretech.data.trip.tripRegistration;

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
public class TripRegistrationPK implements Serializable {
    @NotNull
    private int idTrip;

    @NotNull
    private int idChild;
}
