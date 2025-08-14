package ptc2025.backend.Models.DTO.careerCycleAvailability;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CareerCycleAvailabilityDTO {

    @NotBlank(message = "El ID de disponibilidad es obligatorio")
    private String id;

    @NotBlank(message = "El ID de la carrera es obligatorio")
    private String careerId;

    @NotBlank(message = "El año académico es obligatorio")
    private String academicYearId;

    @NotBlank(message = "El código de ciclo es obligatorio")
    private String cycleCode;

    private Integer maxCapacity;

    @NotBlank(message = "El estado de actividad es obligatorio")
    private Boolean isActive = true;

    public CareerCycleAvailabilityDTO() { }

    public CareerCycleAvailabilityDTO(String id, String careerId, String academicYearId, String cycleCode, Integer maxCapacity, Boolean isActive) {
        this.id = id;
        this.careerId = careerId;
        this.academicYearId = academicYearId;
        this.cycleCode = cycleCode;
        this.maxCapacity = maxCapacity;
        this.isActive = isActive;
    }
}

