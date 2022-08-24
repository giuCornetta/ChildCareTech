package it.polimi.inginf.childcaretech.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
public class CreatedChild implements Serializable { //implementando serializable può essere inviato tramite JSON ??

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
    private int parent1;

    @ManyToOne
    private int parent2;

    @NotNull
    private int doctor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CreatedChild child = (CreatedChild) o;
        return Objects.equals(id, child.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

