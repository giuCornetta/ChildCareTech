package it.polimi.inginf.childcaretech.data.cafeteria.dishAllergen;

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
public class DishAllergenPK implements Serializable {
    @NotNull
    private int idDish;

    @NotNull
    private int idAllergen;
}
