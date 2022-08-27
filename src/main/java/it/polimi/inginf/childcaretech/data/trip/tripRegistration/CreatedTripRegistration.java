package it.polimi.inginf.childcaretech.data.trip.tripRegistration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="tripregistration")
public class CreatedTripRegistration {
    @EmbeddedId
    private TripRegistrationPK primarykey;

    @NotNull
    private int group;

    @NotNull
    private String bus;
}
