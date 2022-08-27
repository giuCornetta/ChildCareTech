package it.polimi.inginf.childcaretech.data.cafeteria.cafeteriaOrder;

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
@Table(name="CAFETERIAORDER")
public class CafeteriaOrder implements Serializable {
    @EmbeddedId
    @NotNull
    private CafeteriaOrderPK primarykey;
}
