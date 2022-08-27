package it.polimi.inginf.childcaretech.data.trip.supervisorStaffTrip;

import it.polimi.inginf.childcaretech.data.Staff;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="supervisorstafftrip")
public class SupervisorStaffTrip implements Serializable {
    @EmbeddedId
    private SupervisorStaffTripPK primarykey;

    @ManyToOne
    @JoinColumn(name = "idSupervisor", referencedColumnName = "ID")
    @NotNull
    @MapsId("idSupervisor")
    private Staff staff;

    @NotNull
    private String bus;
}
