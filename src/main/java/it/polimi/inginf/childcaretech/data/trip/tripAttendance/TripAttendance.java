package it.polimi.inginf.childcaretech.data.trip.tripAttendance;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="tripattendance")
public class TripAttendance implements Serializable {
    @EmbeddedId
    private TripAttendancePK primarykey;
}
