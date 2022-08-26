package it.polimi.inginf.childcaretech.data.childAllergen;

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
public class ChildAllergenPK implements Serializable {
    @NotNull
    private int idChild;

    @NotNull
    private int idAllergen;
}
