package it.polimi.inginf.childcaretech.data.cafeteria.dishAllergen;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name="DISHALLERGEN")
public class DishAllergen implements Serializable {
    @EmbeddedId
    @NotNull
    private DishAllergenPK primarykey;
}
