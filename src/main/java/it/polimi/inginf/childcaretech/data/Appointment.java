package it.polimi.inginf.childcaretech.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor //Generates a Constructor with required arguments
@NoArgsConstructor
public class Appointment {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @JoinColumn(name = "ID_Child")
    @ManyToOne
    private Child child;

    @NotNull
    @JoinColumn(name = "ID_Doctor")
    @ManyToOne
    private Doctor doctor;

    @NotNull
    @JoinColumn(name = "ID_Staff")
    @ManyToOne
    private Staff staff;

    @NotNull
    //@DateTimeFormat(pattern="dd/MM/yyyy")
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate date;

    @NotNull
    //@DateTimeFormat(pattern="HH:mm")
    @JsonFormat(pattern="HH:mm")
    private LocalTime time;

    @Nullable
    private String description;

    public Appointment(Child child, Doctor doctor, Staff staff, LocalDate date, LocalTime time, String description) {
        this.child = child;
        this.doctor = doctor;
        this.staff = staff;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Appointment that = (Appointment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
