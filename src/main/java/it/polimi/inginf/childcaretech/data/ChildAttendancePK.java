package it.polimi.inginf.childcaretech.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
public class ChildAttendancePK implements Serializable {

    @NotNull
    @Column(name="ID_Child")
    private int idChild;

    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate date;

    public ChildAttendancePK(int id, LocalDate date) {
        this.idChild = id;
        this.date = date;
    }


    /** getters and setters **/
}
