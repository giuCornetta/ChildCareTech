package it.polimi.inginf.childcaretech.data.childAttendance;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScannedChildAttendance implements Serializable {
    private int id;
    private String cf;
    private String name;
    private String surname;
    private LocalTime time;
}
