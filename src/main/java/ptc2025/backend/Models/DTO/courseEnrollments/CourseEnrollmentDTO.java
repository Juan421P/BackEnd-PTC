package ptc2025.backend.Models.DTO.courseEnrollments;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseEnrollmentDTO {

    @NotBlank(message = "El ID de inscripción no puede estar vacío")
    private String id;

    @NotBlank(message = "El estudiante es obligatorio")
    private String studentId;

    @NotBlank(message = "La oferta del curso es obligatoria")
    private String offeringId;

    private LocalDate enrollmentDate;
    private Double grade;
    private Boolean isActive = true;

    public CourseEnrollmentDTO() { }

    public CourseEnrollmentDTO(String id, String studentId, String offeringId,
                               LocalDate enrollmentDate, Double grade, Boolean isActive) {
        this.id = id;
        this.studentId = studentId;
        this.offeringId = offeringId;
        this.enrollmentDate = enrollmentDate;
        this.grade = grade;
        this.isActive = isActive;
    }
}
