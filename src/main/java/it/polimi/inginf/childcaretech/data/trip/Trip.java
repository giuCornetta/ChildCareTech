package it.polimi.inginf.childcaretech.data.trip;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity //Needed for Spring JPA
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate departureDate;

    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate returnDate;

    @NotNull
    private String departureCity;

    @NotNull
    private String arrivalCity;
}
