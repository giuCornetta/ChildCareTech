package it.polimi.inginf.childcaretech.Data;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor //Generates a Constructor with required arguments
@NoArgsConstructor /*Lombok @RequiredArgsConstructor will not generate any argument for: Non-final fields. Initialized final fields. static fields. Initialized non-null fields.*/
@Entity //Needed for Spring JPA
public class Bambino implements Serializable { //implementando serializable può essere inviato tramite JSON ??

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codice;

    @NotNull
    private String cf;

    @NotNull
    private String nome;

    @NotNull
    private String cognome;

    @NotNull
    private Date dob; //magari devo modificare a Date nel futuro

    /*@ManyToOne //FIXME (ci possono essere più bambini per un genitore)
    private Parent parent1;
    */

    @NotNull
    private String indirizzo;

    @ManyToOne
    @JoinColumn(name = "IDGenitore1")
    private Genitore genitore1;

    @ManyToOne
    @JoinColumn(name="IDGenitore2")
    private Genitore genitore2;

    /*@NotNull
    @Column(name="IDGenitore1")
    private int idGenitore1;

    @Nullable
    @Column(name="IDGenitore2")
    private Integer idGenitore2; //has to be Integer because can be null*/

    @ManyToOne
    @JoinColumn(name="IDSpecialista")
    private Specialista specialista;

    /*public class Province {

        @Id
        Long id;

        @Column(nullable = false)
        String name;

        @JsonManagedReference
        @OneToMany(mappedBy = "province")
        List<City> cities;

    }

    public class City{
    @Column(nullable = false)
    String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    Province province;

}
     */
}
