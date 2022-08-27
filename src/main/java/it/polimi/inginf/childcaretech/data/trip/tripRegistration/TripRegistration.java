package it.polimi.inginf.childcaretech.data.trip.tripRegistration;

import it.polimi.inginf.childcaretech.data.CreatedChild;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="tripregistration")
public class TripRegistration implements Serializable {
    @EmbeddedId
    private TripRegistrationPK primarykey;

    @ManyToOne
    @JoinColumn(name = "idChild", referencedColumnName = "ID")
    @NotNull
    @MapsId("idChild")
    private CreatedChild child;

    @NotNull
    private int group;

    @NotNull
    private String bus;
}
