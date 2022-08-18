package it.polimi.inginf.childcaretech.Data;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor //Generates a Constructor with required arguments
@NoArgsConstructor
public class Staff {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String cf;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String type; //tipo di staff member
    @NotNull
    private String telephone;
    @NotNull
    private String email;

    private Boolean tutorial;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Staff staff = (Staff) o;
        return Objects.equals(id, staff.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
