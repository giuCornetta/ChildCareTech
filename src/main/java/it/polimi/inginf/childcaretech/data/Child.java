package it.polimi.inginf.childcaretech.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor //Generates a Constructor with required arguments
@NoArgsConstructor /*Lombok @RequiredArgsConstructor will not generate any argument for: Non-final fields. Initialized final fields. static fields. Initialized non-null fields.*/
@Entity //Needed for Spring JPA
public class Child implements Serializable { //implementando serializable può essere inviato tramite JSON ??

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String cf;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dob; //magari devo modificare a Date nel futuro

    @NotNull
    private String address;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ID_Parent1")
    private Parent parent1;

    @ManyToOne
    @JoinColumn(name="ID_Parent2")
    @Nullable
    private Parent parent2;

    @NotNull
    @ManyToOne
    @JoinColumn(name="ID_Doctor")
    private Doctor doctor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Child child = (Child) o;
        return Objects.equals(id, child.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
