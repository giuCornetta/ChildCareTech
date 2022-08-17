package it.polimi.inginf.childcaretech.Data;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Data
@Entity
@RequiredArgsConstructor //Generates a Constructor with required arguments
@NoArgsConstructor /*Lombok @RequiredArgsConstructor will not generate any argument for: Non-final fields. Initialized final fields. static fields. Initialized non-null fields.*/
public class Genitore {

    @Id
    private int id;
    @NotNull
    private String cf;
    @NotNull
    private String nome;
    @NotNull
    private String cognome;
    @NotNull
    private String email;
    /*@OneToMany
    private TelephoneNumber;*/

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Email> emails;*/

    //Telephones
}
