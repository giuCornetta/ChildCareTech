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
    @JoinColumn(name = "idParent1")
    private Parent parent1;

    @ManyToOne
    @JoinColumn(name="idParent2")
    @Nullable
    private Parent parent2;

    @NotNull
    @ManyToOne
    @JoinColumn(name="idDoctor")
    private Doctor doctor;

    public Child(int id, String cf, String name, String surname, Date dob, String address, Parent parent1, Parent parent2, Doctor doctor) {
        this.id = id;
        this.cf = cf;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.address = address;
        this.parent1 = parent1;
        this.parent2 = parent2;
        this.doctor = doctor;
    }


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
