package it.polimi.inginf.childcaretech.Data;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Staff {

    @Id
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

}
