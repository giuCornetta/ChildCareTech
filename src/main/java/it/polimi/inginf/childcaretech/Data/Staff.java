package it.polimi.inginf.childcaretech.Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.AccessLevel;
import lombok.*;
import org.hibernate.Hibernate;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
//Generates a Constructor with required arguments
@NoArgsConstructor(access= AccessLevel.PUBLIC, force=true)
@RequiredArgsConstructor
@JsonIgnoreProperties("password")
public class Staff implements UserDetails {

    private static final long serialVersionUID = 1L;
    //TODO error se ci sono errori in alcuni vincoli
    //TODO third part access
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private final String cf;

    @NotNull
    private final String name;

    @NotNull
    private final String surname;

    @NotNull
    private final String username;

    @NotNull
    private final String password;

    @NotNull
    private final String type; //type of staff member

    @NotNull
    private final String telephone;

    @NotNull
    private final String email;

    @Nullable
    private Boolean tutorial;

    /**
     * Returns a collection of authorities granted to the user
     * @return A collection of GrantedAuthority objects
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Staff staff = (Staff) o;
        return Objects.equals(id, staff.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
