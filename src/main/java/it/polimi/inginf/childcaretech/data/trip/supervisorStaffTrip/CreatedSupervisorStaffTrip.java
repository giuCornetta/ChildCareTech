package it.polimi.inginf.childcaretech.data.trip.supervisorStaffTrip;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="supervisorstafftrip")
public class CreatedSupervisorStaffTrip implements Serializable {
    @EmbeddedId
    private SupervisorStaffTripPK primarykey;

    @NotNull
    private String bus;
}

