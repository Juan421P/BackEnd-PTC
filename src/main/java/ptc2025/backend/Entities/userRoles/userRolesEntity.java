package ptc2025.backend.Entities.userRoles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "USERROLES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class userRolesEntity {
    @Id
    @GenericGenerator(name = "idUserRole", strategy = "guid")
    @GeneratedValue(generator = "idUserRole")
    @Column(name = "USERROLEID")
    private String userRoleid;

    @Column(name = "USERID")
    private String Userid;

    @Column(name = "ROLETYPE")
    private String roleType;
}
