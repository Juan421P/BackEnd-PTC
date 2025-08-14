package ptc2025.backend.Entities.InstrumentType;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "INSTRUMENTTYPES")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class InstrumentTypeEntity {
    @Id
    @GenericGenerator(name = "idInstrumentType", strategy = "guid")
    @GeneratedValue(generator = "idInstrumentType")
    @Column(name = "INSTRUMENTTYPEID")
    private String instrumentTypeID;
    @Column(name = "UNIVERSITYID")
    private String universityID;
    @Column(name = "INSTRUMENTTYPENAME")
    private String instrumentTypeName;
}
