package it.polimi.inginf.childcaretech.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor //Generates a Constructor with required arguments
@NoArgsConstructor /*Lombok @RequiredArgsConstructor will not generate any argument for: Non-final fields. Initialized final fields. static fields. Initialized non-null fields.*/
@Entity //Needed for Spring JPA
public class ChildAttendance { //implementando serializable può essere inviato tramite JSON ??

    /*@NotNull
    @ManyToOne
    @JoinColumn(name = "ID_Child")
    private Child child;*/

    @EmbeddedId
    private ChildAttendancePK primaryKey;

    @ManyToOne
    @JoinColumn(name = "ID_Child", referencedColumnName = "ID")
    @NotNull
    @MapsId("idChild")
    private Child child;

    @JsonFormat(pattern="HH:mm")
    private LocalTime entranceTime;

    @JsonFormat(pattern="HH:mm")
    private LocalTime exitTime;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}


