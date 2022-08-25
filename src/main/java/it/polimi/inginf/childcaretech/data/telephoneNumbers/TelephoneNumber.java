package it.polimi.inginf.childcaretech.data.telephoneNumbers;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@ToString
//@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TELEPHONENUMBER")
public class TelephoneNumber implements Serializable {

    @NotNull
    @EmbeddedId
    private TelephoneNumberPK primarykey;

    @Nullable
    private String description;

}


