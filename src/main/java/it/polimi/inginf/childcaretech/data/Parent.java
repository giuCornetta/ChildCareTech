package it.polimi.inginf.childcaretech.data;

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
@NoArgsConstructor /*Lombok @RequiredArgsConstructor will not generate any argument for: Non-final fields. Initialized final fields. static fields. Initialized non-null fields.*/
public class Parent {

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
    private String email;
    /*@OneToMany
    private TelephoneNumber;*/

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Email> emails;*/

    //Telephones

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Parent parent = (Parent) o;
        return Objects.equals(id, parent.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
