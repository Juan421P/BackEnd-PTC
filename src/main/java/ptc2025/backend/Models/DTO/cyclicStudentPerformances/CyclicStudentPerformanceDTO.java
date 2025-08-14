package ptc2025.backend.Models.DTO.cyclicStudentPerformances;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CyclicStudentPerformanceDTO {

    private String id;

    @NotBlank(message = "El ID del estudiante es obligatorio")
    private String studentId;

    @NotBlank(message = "El código del ciclo es obligatorio")
    private String cycleCode;

    private String academicYearId;
    private Double averageGrade;
    private Boolean passed;
    private Boolean isActive = true;

    public CyclicStudentPerformanceDTO(String id, String studentId, String cycleCode, String academicYearId, Double averageGrade, Boolean passed, Boolean isActive) {
    }
}
