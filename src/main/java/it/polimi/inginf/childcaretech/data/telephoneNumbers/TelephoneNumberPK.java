package it.polimi.inginf.childcaretech.data.telephoneNumbers;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@ToString
@Getter
public class TelephoneNumberPK implements Serializable {
    @NotNull
    private int idParent;

    @NotNull
    private String telephone;


   // select telephoneNumber.primaryKey.idParent

}
