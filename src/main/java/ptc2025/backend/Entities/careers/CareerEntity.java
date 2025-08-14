package ptc2025.backend.Entities.careers;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CAREERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerEntity {

    @Id
    @Column(name = "CAREERID")
    private String id;

    @Column(name = "CAREERNAME", nullable = false)
    private String name;

    @Column(name = "CAREERDESCRIPTION")
    private String description;

    @Column(name = "FACULTYID")
    private String facultyId;

    @Column(name = "ISACTIVE")
    private Boolean isActive;
}

