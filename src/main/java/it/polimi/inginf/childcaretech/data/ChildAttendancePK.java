package it.polimi.inginf.childcaretech.data;

import com.sun.istack.NotNull;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ChildAttendancePK implements Serializable {

    @NotNull
    private int idChild;

    @NotNull

    private int date;

    /** getters and setters **/
}
