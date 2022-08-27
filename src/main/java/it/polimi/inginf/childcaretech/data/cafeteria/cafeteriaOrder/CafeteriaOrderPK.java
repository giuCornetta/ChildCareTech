package it.polimi.inginf.childcaretech.data.cafeteria.cafeteriaOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@ToString
@Getter
public class CafeteriaOrderPK implements Serializable {
    @NotNull
    private int idChild;
    @NotNull
    private int idDish;
    @NotNull
    private LocalDate date; //No need to format

}
