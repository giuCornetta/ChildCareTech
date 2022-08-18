package it.polimi.inginf.childcaretech.Data;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
