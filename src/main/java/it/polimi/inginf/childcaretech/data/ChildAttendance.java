package it.polimi.inginf.childcaretech.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
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

    /*@NotNull
    private String name;

    @NotNull
    private String surname;*/

    @JsonFormat(pattern="HH:mm")
    @Column(name="entrancetime")
    @Nullable
    private LocalTime entranceTime;

    @JsonFormat(pattern="HH:mm")
    @Column(name="exittime")
    @Nullable
    private LocalTime exitTime;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public ChildAttendance(Child child, LocalDate date, LocalTime entranceTime, LocalTime exitTime){
        setChild(child);
        setPrimaryKey(new ChildAttendancePK(child.getId(), date));
        setEntranceTime(entranceTime);
        setExitTime(exitTime);
    }
}


