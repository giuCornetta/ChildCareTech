package it.polimi.inginf.childcaretech.Data;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@RequiredArgsConstructor //Generates a Constructor with required arguments
@NoArgsConstructor /*Lombok @RequiredArgsConstructor will not generate any argument for: Non-final fields. Initialized final fields. static fields. Initialized non-null fields.*/
public class Contatto {
    @Id
    private int id;
    @NotNull
    private String cf;
    @NotNull
    @Column(name="codbambino")
    private int codBambino;
    @NotNull
    private String nome;
    @NotNull
    private String cognome;
    @Nullable
    private String telefono;
    /*@OneToMany
    private TelephoneNumber;*/

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Email> emails;*/

    //Telephones
}
