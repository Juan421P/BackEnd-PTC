package ptc2025.backend.Entities.careerSocialServiceProjects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "CAREERSOCIALSERVICEPROJECTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerSocialServiceProjectEntity {

    @Id
    @Column(name = "PROJECTID")
    private String id;

    @Column(name = "CAREERID", nullable = false)
    private String careerId;

    @Column(name = "PROJECTNAME", nullable = false)
    private String projectName;

    @Column(name = "SUPERVISORNAME")
    private String supervisorName;

    @Column(name = "STARTDATE")
    private LocalDate startDate;

    @Column(name = "ENDDATE")
    private LocalDate endDate;

    @Column(name = "ISACTIVE")
    private Boolean isActive;
}
