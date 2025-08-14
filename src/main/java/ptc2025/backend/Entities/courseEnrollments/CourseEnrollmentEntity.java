package ptc2025.backend.Entities.courseEnrollments;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "COURSEENROLLMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseEnrollmentEntity {

    @Id
    @Column(name = "ENROLLMENTID")
    private String id;

    @Column(name = "STUDENTID", nullable = false)
    private String studentId;

    @Column(name = "OFFERINGID", nullable = false)
    private String offeringId;

    @Column(name = "ENROLLMENTDATE")
    private LocalDate enrollmentDate;

    @Column(name = "GRADE")
    private Double grade;

    @Column(name = "ISACTIVE")
    private Boolean isActive;
}
