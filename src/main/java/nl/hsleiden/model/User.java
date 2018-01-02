package nl.hsleiden.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.security.Principal;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;

import javax.persistence.*;

@Entity
@Table(name = "user")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "User.FIND_ALL",
                query = "SELECT * FROM user;",
                resultClass = User.class
        ),
        @NamedNativeQuery(
                name = "User.FIND_BY_EMAIL",
                query = "SELECT * FROM user WHERE email = :email",
                resultClass = User.class
        )
})
public class User implements Principal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private String role;
    private String firstname;
    private String suffix;
    private String lastname;
    private boolean active;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean hasRole(String role) {
        return this.role.equals(role);
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    @JsonIgnore
    public String getName() {
        return firstname + " " + suffix + " " + lastname;
    }
}
