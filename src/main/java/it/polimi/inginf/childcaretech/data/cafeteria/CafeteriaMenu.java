package it.polimi.inginf.childcaretech.data.cafeteria;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="CAFETERIAMENU")
public class CafeteriaMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate date;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "idFirstCourse")
    private Dish firstCourse;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "idSecondCourse")
    private Dish secondCourse;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "idSideDish")
    private Dish sideDish;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "idDessert")
    private Dish dessert;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "idFruit")
    private Dish fruit;
}
