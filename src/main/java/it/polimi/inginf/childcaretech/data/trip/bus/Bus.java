package it.polimi.inginf.childcaretech.data.trip.bus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Bus implements Serializable {
    @EmbeddedId
    private BusPK primarykey;
}
