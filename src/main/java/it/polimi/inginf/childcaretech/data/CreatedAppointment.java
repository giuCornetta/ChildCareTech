package it.polimi.inginf.childcaretech.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@ToString
public class CreatedAppointment {

    @NotNull
    private int id;

    @NotNull
    private int idChild;

    @NotNull
    private int idDoctor;

    @NotNull
    private int idStaff;

    @NotNull
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate date;

    @NotNull
    @JsonFormat(pattern="HH:mm")
    private LocalTime time;

    @Nullable
    private String description;

    public CreatedAppointment(int child, int doctor, int staff, LocalDate date, LocalTime time, String description) {
        this.idChild = child;
        this.idDoctor = doctor;
        this.idStaff = staff;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatedAppointment that = (CreatedAppointment) o;
        return id == that.id && idChild == that.idChild && idDoctor == that.idDoctor && idStaff == that.idStaff && date.equals(that.date) && time.equals(that.time) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idChild, idDoctor, idStaff, date, time, description);
    }
}
