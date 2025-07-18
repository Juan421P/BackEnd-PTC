package ptc2025.backend.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "USERS")
@Getter @Setter @ToString @EqualsAndHashCode
public class UserEntity {

    @Id
    @GenericGenerator(name = "IDusers", strategy = "guid")
    @GeneratedValue(generator = "IDusers")
    @Column(name = "USERID")
    private String id;
    @Column(name = "UNIVERSITYID")
    private String universityID;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "USERNAME")
    private String usuario;
    @Column(name = "PASSWORD")
    private String contrasena;
    @Column(name = "CREATEDAT")
    private Date fechaCreacion;


}
