package it.polimi.inginf.childcaretech.data.childAllergen;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="CHILDALLERGEN")
public class ChildAllergen implements Serializable {
    @EmbeddedId
    @NotNull
    private ChildAllergenPK primarykey;

    public ChildAllergen(ChildAllergenPK childAllergenPK){
        this.primarykey = childAllergenPK;
    }

}
