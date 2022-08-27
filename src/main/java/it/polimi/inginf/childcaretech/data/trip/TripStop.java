package it.polimi.inginf.childcaretech.data.trip;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="tripstop")
public class TripStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private int idTrip;

    @NotNull
    private String stopName;

    @NotNull
    @JsonFormat(pattern="HH:mm")
    private LocalTime arrivalTime;

    @NotNull
    @JsonFormat(pattern="HH:mm")
    private LocalTime departingTime;

    @NotNull
    @Column(name="return")
    private boolean wayBack;

    //FIXME andata o ritorno??
}
