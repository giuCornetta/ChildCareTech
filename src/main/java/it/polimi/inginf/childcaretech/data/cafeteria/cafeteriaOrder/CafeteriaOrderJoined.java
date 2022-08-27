package it.polimi.inginf.childcaretech.data.cafeteria.cafeteriaOrder;

import it.polimi.inginf.childcaretech.data.CreatedChild;
import it.polimi.inginf.childcaretech.data.cafeteria.Dish;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name="CAFETERIAORDER")
public class CafeteriaOrderJoined implements Serializable{
    @EmbeddedId
    @NotNull
    private CafeteriaOrderPK primarykey;

    @ManyToOne
    @JoinColumn(name = "idChild", referencedColumnName = "ID")
    @NotNull
    @MapsId("idChild")
    private CreatedChild child;

    @ManyToOne
    @JoinColumn(name = "idDish", referencedColumnName = "ID")
    @NotNull
    @MapsId("idDish")
    private Dish dish;
}
