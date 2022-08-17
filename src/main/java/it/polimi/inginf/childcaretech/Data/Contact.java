package it.polimi.inginf.childcaretech.Data;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@RequiredArgsConstructor //Generates a Constructor with required arguments
@NoArgsConstructor /*Lombok @RequiredArgsConstructor will not generate any argument for: Non-final fields. Initialized final fields. static fields. Initialized non-null fields.*/
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull
    private String cf;
    @NotNull
    private int idChild;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @Nullable
    private String telephone;
    /*@OneToMany
    private TelephoneNumber;*/

    /*@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
    private List<Email> emails;*/

    //Telephones
}
