package ptc2025.backend.Entities.studentCycleEnrollments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "STUDENTCYCLEENROLLMENTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCycleEnrollmentEntity {

    @Id
    @Column(name = "ENROLLMENTID")
    private String id;

    @Column(name = "STUDENTID", nullable = false)
    private String studentId;

    @Column(name = "CYCLEID", nullable = false)
    private String cycleId;

    @Column(name = "ENROLLMENTDATE")
    private LocalDate enrollmentDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ISACTIVE")
    private Boolean isActive;
}


