package ptc2025.backend.Entities.Localities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "LOCALITIES")
@Getter @Setter @ToString
@EqualsAndHashCode
public class LocalitiesEntity {

    @Id
    @GenericGenerator(name = "idLocality", strategy = "guid")
    @GeneratedValue (generator = "idLocality")
    @Column(name = "LOCALITYID")
    private String localityID;
    @Column(name = "UNIVERSITYID")
    private String universityID;
    @Column(name = "ISMAINLOCALITY")
    private Boolean isMainLocality;
    @Column(name = "ADDRESS")
    private String address;
    @Column (name = "PHONENUMBER")
    private String phoneNumber;

}