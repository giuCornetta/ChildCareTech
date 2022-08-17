package it.polimi.inginf.childcaretech.Data;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor //Generates a Constructor with required arguments
@NoArgsConstructor /*Lombok @RequiredArgsConstructor will not generate any argument for: Non-final fields. Initialized final fields. static fields. Initialized non-null fields.*/
public class Doctor {
    @Id
    private int id;
    @NotNull
    private String cf;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String type;
    @NotNull
    private String email;
    @NotNull
    private String telephone;
    @Nullable
    private String address;
}

