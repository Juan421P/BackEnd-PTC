package ptc2025.backend.Models.DTO.studentCycleEnrollments;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class StudentCycleEnrollmentDTO {

    private String id;

    @NotBlank(message = "El ID del estudiante es obligatorio")
    private String studentId;

    @NotBlank(message = "El ID del ciclo es obligatorio")
    private String cycleId;

    private LocalDate enrollmentDate;
    private String status;
    private Boolean isActive = true;


    public StudentCycleEnrollmentDTO(String id, String studentId, String cycleId, LocalDate enrollmentDate, String status, Boolean isActive) {
    }
}
