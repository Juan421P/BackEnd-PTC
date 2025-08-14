package ptc2025.backend.Models.DTO.careerSocialServiceProjects;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CareerSocialServiceProjectDTO {

    @NotBlank(message = "El ID del proyecto es obligatorio")
    private String id;

    @NotBlank(message = "La carrera es obligatoria")
    private String careerId;

    @NotBlank(message = "El nombre del proyecto es obligatorio")
    private String projectName;

    private String supervisorName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive = true;

    public CareerSocialServiceProjectDTO() { }

    public CareerSocialServiceProjectDTO(String id, String careerId, String projectName, String supervisorName,
                                         LocalDate startDate, LocalDate endDate, Boolean isActive) {
        this.id = id;
        this.careerId = careerId;
        this.projectName = projectName;
        this.supervisorName = supervisorName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }
}
